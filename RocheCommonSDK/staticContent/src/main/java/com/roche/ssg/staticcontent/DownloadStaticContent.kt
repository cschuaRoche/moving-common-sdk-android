package com.roche.ssg.staticcontent

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.annotation.VisibleForTesting
import com.roche.ssg.staticcontent.entity.ManifestInfo
import com.roche.ssg.staticcontent.entity.MaterialInfo
import com.roche.ssg.staticcontent.entity.StaticContentInfo
import com.roche.ssg.utils.NetworkUtils
import com.roche.ssg.utils.UnZipUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import java.util.LinkedList
import java.util.Queue

class DownloadStaticContent private constructor(context: Context) {
    private val weakReference = WeakReference(context)
    private val queue: Queue<MaterialInfo> = LinkedList()

    suspend fun downloadStaticAssets(
        staticContentInfo: StaticContentInfo,
        result: (DownloadStaticContentResult) -> Unit
    ) {
        queue.add(MaterialInfo(staticContentInfo, result))
        if (queue.size == 1) {
            processDownload()
        }
    }

    private tailrec suspend fun processDownload() {
        val materialInfo = queue.peek()
        materialInfo?.let {
            val context =
                weakReference.get() ?: throw IllegalStateException(EXCEPTION_UNKNOWN_ERROR)
            try {
                // target sub directory should not be empty
                if (it.staticContentInfo.targetSubDir.isBlank()) {
                    throw IllegalArgumentException(EXCEPTION_TARGET_SUB_DIRECTORY_EMPTY)
                }

                // read manifest file and get the url
                val manifestInfo = getInfoFromManifest(it.staticContentInfo)

                // check if the url is not a zip file type then throw exception
                val fileExtension = manifestInfo.path.substring(manifestInfo.path.lastIndexOf("."))
                if (fileExtension.equals(ZIPPED_FILE_EXTENSION, true).not()) {
                    throw IllegalStateException(EXCEPTION_INVALID_MANIFEST_FILE_FORMAT)
                }

                // check free space to download the file
                if (context.filesDir.usableSpace <= manifestInfo.fileSize) {
                    throw IllegalStateException(EXCEPTION_INSUFFICIENT_STORAGE)
                }

                // download the file
                val zipPath = downloadFromUrl(
                    context,
                    it.staticContentInfo,
                    manifestInfo.path,
                    it.result
                )

                // check free space to unzip the file
                if (context.filesDir.usableSpace <= manifestInfo.originalSize) {
                    throw IllegalStateException(EXCEPTION_INSUFFICIENT_STORAGE)
                }

                // unzip the file and save the path to shared pref
                val unzipPath = unzipFile(context, zipPath, it.staticContentInfo)
                DownloadStaticContentSharedPref.setFilePath(
                    context,
                    it.staticContentInfo.targetSubDir,
                    it.staticContentInfo.appVersion,
                    it.staticContentInfo.locale,
                    it.staticContentInfo.fileKey,
                    unzipPath
                )
                // Delete zipped file
                File(zipPath).delete()
                // check and delete old version
                checkAndDeleteOldVersionData(
                    context,
                    it.staticContentInfo.targetSubDir,
                    it.staticContentInfo.appVersion
                )
                it.result(DownloadStaticContentResult.Success(it.staticContentInfo, unzipPath))
            } catch (e: Exception) {
                if (e.message == EXCEPTION_NOT_MODIFIED) {
                    // return existing file path as manifest is not modified
                    val unzipPath = DownloadStaticContentSharedPref.getFilePath(
                        context,
                        it.staticContentInfo.targetSubDir,
                        it.staticContentInfo.appVersion,
                        it.staticContentInfo.locale,
                        it.staticContentInfo.fileKey
                    )
                    it.result(DownloadStaticContentResult.Success(it.staticContentInfo, unzipPath))
                } else {
                    it.result(
                        DownloadStaticContentResult.Failure(
                            it.staticContentInfo,
                            e.message ?: ""
                        )
                    )
                }
            }
            // remove from the queue
            queue.remove()
        }

        if (queue.isNotEmpty()) {
            processDownload()
        }
    }

    /**
     * get manifest info based on the app version, locale and file key from given manifest
     *
     * @param context Context
     * @param manifestUrl Url of the manifest file
     * @param appVersion Application version
     * @param locale Locale
     * @param fileKey file key for which static assets needs to be downloaded (e.g. user_manual)
     * @param targetSubDir sub directory where the files will be downloaded to
     * @param allowWifiOnly download asset on WIFI only (Default is false).
     *
     * @return returns ManifestInfo object
     */
    @Throws(
        IllegalStateException::class,
        IllegalArgumentException::class,
        IOException::class,
        JSONException::class
    )
    suspend fun getInfoFromManifest(staticContentInfo: StaticContentInfo): ManifestInfo {
        val context = weakReference.get() ?: throw IllegalStateException(EXCEPTION_UNKNOWN_ERROR)
        checkConnection(context, staticContentInfo.allowWifiOnly)

        return withContext(Dispatchers.IO) {
            var urlConnection: HttpURLConnection? = null
            try {
                urlConnection = getUrlConnection(staticContentInfo.manifestUrl)

                // get etag and downloaded file path value from secure shared preference
                val etag = DownloadStaticContentSharedPref.getETag(
                    context,
                    staticContentInfo.targetSubDir,
                    staticContentInfo.appVersion,
                    staticContentInfo.locale,
                    staticContentInfo.fileKey
                )
                val filePath = DownloadStaticContentSharedPref.getFilePath(
                    context,
                    staticContentInfo.targetSubDir,
                    staticContentInfo.appVersion,
                    staticContentInfo.locale,
                    staticContentInfo.fileKey
                )
                if (etag.isNotBlank() && filePath.isNotBlank()) {
                    // If both eTag and file path are available then add etag in header
                    urlConnection.addRequestProperty("If-None-Match", etag)
                    urlConnection.useCaches = false
                }

                if (HttpURLConnection.HTTP_NOT_MODIFIED == urlConnection.responseCode) {
                    throw IllegalStateException(EXCEPTION_NOT_MODIFIED)
                } else {
                    val newETag = urlConnection.headerFields[HEADER_KEY_ETAG]
                    val inputStream = BufferedReader(
                        InputStreamReader(
                            urlConnection.inputStream
                        )
                    )
                    val jsonResponse = readStream(inputStream)
                    val manifestInfo = parseManifest(jsonResponse, staticContentInfo)
                    // Cache new eTag to secured shared pref
                    if (newETag != null && newETag.isNotEmpty()) {
                        DownloadStaticContentSharedPref.setETag(
                            context,
                            staticContentInfo.targetSubDir,
                            staticContentInfo.appVersion,
                            staticContentInfo.locale,
                            staticContentInfo.fileKey,
                            newETag[0]
                        )
                    }
                    manifestInfo
                }
            } catch (e: IOException) {
                Log.d(LOG_TAG, "error: $e")
                throw e
            } finally {
                urlConnection?.disconnect()
            }
        }
    }

    /**
     * Download a file to the app's files directory.
     *
     * @param context application context
     * @param fileUrl the file url
     * @param progress callback which will return the progress of the download
     * @param targetSubDir sub directory where the files will be downloaded to
     * @param allowWifiOnly download asset on WIFI only (Default is false).
     *
     * @return downloaded zipped file's path
     */
    @Throws(IOException::class, IllegalStateException::class)
    suspend fun downloadFromUrl(
        context: Context,
        staticContentInfo: StaticContentInfo,
        fileUrl: String,
        progress: (DownloadStaticContentResult) -> Unit
    ): String {
        checkConnection(context, staticContentInfo.allowWifiOnly)
        return withContext(Dispatchers.IO) {
            val fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1)
            val file =
                File(context.filesDir.toString() + File.separator + staticContentInfo.targetSubDir + File.separator + staticContentInfo.appVersion)
            if (file.exists().not()) {
                file.mkdirs()
            }
            val path = file.path + File.separator + fileName

            // download the file
            val request = DownloadManager.Request(Uri.parse(fileUrl))
            if (staticContentInfo.allowWifiOnly) {
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            } else {
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            }
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            val downloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadId = downloadManager.enqueue(request)
            val isDownloadSuccessful = trackProgress(
                downloadId,
                downloadManager,
                staticContentInfo,
                progress
            )
            if (!isDownloadSuccessful) {
                throw IllegalStateException(EXCEPTION_DOWNLOAD_FAILED)
            }
            moveDownloadToInternalDir(
                context,
                downloadId,
                downloadManager,
                path
            )
            path
        }
    }

    /**
     * Unzip the file
     *
     * @param context the application context
     * @param filePath location of the zipped file
     * @param targetSubDir sub directory where the files will be unzipped to.
     * If not provided then it will unzip to app's files directory
     *
     * @return unzip file path if zip file was successfully unzipped to the app's directory,
     * otherwise throws error
     */
    @Throws(IllegalStateException::class)
    fun unzipFile(
        context: Context,
        filePath: String,
        staticContentInfo: StaticContentInfo
    ): String {
        val subDirPath =
            staticContentInfo.targetSubDir + File.separator + staticContentInfo.appVersion
        val unzipPath = UnZipUtils.unzipFromAppFiles(filePath, context, subDirPath)
        if (unzipPath == null) {
            Log.e(LOG_TAG, "error occurred in unzipping the file")
            throw IllegalStateException(EXCEPTION_UNZIPPING_FILE)
        }
        return unzipPath
    }

    private fun checkConnection(context: Context, allowWifiOnly: Boolean) {
        if (allowWifiOnly) {
            if (NetworkUtils.isWifiConnected(context).not()) {
                throw IllegalStateException(EXCEPTION_WIFI_NOT_AVAILABLE)
            }
        } else {
            if (NetworkUtils.hasInternetConnection(context).not()) {
                throw IllegalStateException(EXCEPTION_NETWORK_NOT_AVAILABLE)
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun getUrlConnection(manifestUrl: String): HttpURLConnection {
        val url = URL(manifestUrl)
        return url.openConnection() as HttpURLConnection
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun readStream(inputStream: BufferedReader): String {
        val sb = StringBuilder()
        var line: String?
        while (inputStream.readLine().also { line = it } != null) {
            sb.append(line)
            Log.i(LOG_TAG, line!!)
        }
        return sb.toString()
    }

    @Throws(IllegalArgumentException::class, JSONException::class)
    private fun parseManifest(
        jsonResponse: String,
        staticContentInfo: StaticContentInfo
    ): ManifestInfo {
        return try {
            val jsonObject = JSONObject(jsonResponse)
            if (jsonObject.has(staticContentInfo.appVersion)) {
                val versionObj = jsonObject.getJSONObject(staticContentInfo.appVersion)
                if (versionObj.has(staticContentInfo.locale)) {
                    val localeObj = versionObj.getJSONObject(staticContentInfo.locale)
                    if (localeObj.has(staticContentInfo.fileKey)) {
                        val fileObj = localeObj.getJSONObject(staticContentInfo.fileKey)
                        ManifestInfo(
                            fileObj.getString(JSON_KEY_PATH),
                            fileObj.getLong(JSON_KEY_FILE_SIZE),
                            fileObj.getLong(JSON_KEY_ORIGINAL_SIZE)
                        )
                    } else {
                        Log.e(LOG_TAG, "Content not found for ${staticContentInfo.fileKey} key")
                        throw IllegalArgumentException(EXCEPTION_MANIFEST_FILE_KEY_NOT_FOUND)
                    }
                } else {
                    Log.e(LOG_TAG, "Content not found for ${staticContentInfo.locale} locale")
                    throw IllegalArgumentException(EXCEPTION_MANIFEST_LOCALE_NOT_FOUND)
                }
            } else {
                Log.e(LOG_TAG, "Content not found for ${staticContentInfo.appVersion} version")
                throw IllegalArgumentException(EXCEPTION_MANIFEST_APP_VERSION_NOT_FOUND)
            }
        } catch (e: JSONException) {
            Log.e(LOG_TAG, "error: $e")
            throw e
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal suspend fun trackProgress(
        downloadId: Long,
        downloadManager: DownloadManager,
        staticContentInfo: StaticContentInfo,
        progress: (DownloadStaticContentResult) -> Unit
    ): Boolean {
        var isDownloadFinished = false
        var isDownloadSuccessful = false
        while (!isDownloadFinished) {
            val cursor = downloadManager.query(DownloadManager.Query().setFilterById(downloadId))
            if (cursor.moveToFirst()) {
                when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                    DownloadManager.STATUS_FAILED -> {
                        isDownloadSuccessful = false
                        isDownloadFinished = true
                    }
                    DownloadManager.STATUS_RUNNING -> {
                        val total =
                            cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                        if (total >= 0) {
                            val downloaded =
                                cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                            withContext(Dispatchers.Main) {
                                progress(
                                    DownloadStaticContentResult.DownloadProgress(
                                        staticContentInfo,
                                        (downloaded * 100L / total).toInt()
                                    )
                                )
                            }
                        }
                    }
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        progress(
                            DownloadStaticContentResult.DownloadProgress(
                                staticContentInfo,
                                100
                            )
                        )
                        isDownloadSuccessful = true
                        isDownloadFinished = true
                    }
                }
            }
            cursor.close()
        }
        return isDownloadSuccessful
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun moveDownloadToInternalDir(
        context: Context,
        downloadId: Long,
        downloadManager: DownloadManager,
        destPath: String
    ) {
        val uri = downloadManager.getUriForDownloadedFile(downloadId)
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputFile = File(destPath)
        val outputStream = FileOutputStream(outputFile)
        val buf = ByteArray(1024)
        var len: Int
        while (run {
                len = inputStream!!.read(buf)
                len
            } > 0) {
            outputStream.write(buf, 0, len)
        }
        outputStream.close()
        inputStream?.close()
        downloadManager.remove(downloadId)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun checkAndDeleteOldVersionData(
        context: Context,
        targetSubDir: String,
        appVersion: String
    ) {
        val existingVersion = DownloadStaticContentSharedPref.getVersion(context, targetSubDir)
        if (existingVersion != appVersion) {
            if (existingVersion.isNotBlank()) {
                // delete old version's data
                val path =
                    context.filesDir.toString() + File.separator + targetSubDir + File.separator + existingVersion
                File(path).deleteRecursively()
                // remove shared pref data
                DownloadStaticContentSharedPref.removeAllKeysOfAppVersion(
                    context,
                    targetSubDir,
                    existingVersion
                )
            }
            // update new version
            DownloadStaticContentSharedPref.setVersion(context, targetSubDir, appVersion)
        }
    }

    companion object {
        private const val LOG_TAG = "DownloadStaticContent"
        private const val ZIPPED_FILE_EXTENSION = ".zip"
        private const val HEADER_KEY_ETAG = "ETag"
        private const val JSON_KEY_PATH = "path"
        private const val JSON_KEY_FILE_SIZE = "fileSize"
        private const val JSON_KEY_ORIGINAL_SIZE = "originalSize"

        const val EXCEPTION_UNKNOWN_ERROR = "Unknown Error"
        const val EXCEPTION_TARGET_SUB_DIRECTORY_EMPTY = "Target sub directory is empty"
        const val EXCEPTION_WIFI_NOT_AVAILABLE = "Wifi Not Available"
        const val EXCEPTION_NETWORK_NOT_AVAILABLE = "Network Not Available"
        const val EXCEPTION_NOT_MODIFIED = "Not Modified"
        const val EXCEPTION_INVALID_MANIFEST_FILE_FORMAT = "Invalid Manifest File Format"
        const val EXCEPTION_MANIFEST_APP_VERSION_NOT_FOUND = "Manifest App Version Not Found"
        const val EXCEPTION_MANIFEST_LOCALE_NOT_FOUND = "Manifest Locale Not Found"
        const val EXCEPTION_MANIFEST_FILE_KEY_NOT_FOUND = "Manifest File Key Not Found"
        const val EXCEPTION_UNZIPPING_FILE = "Error In Unzipping The File"
        const val EXCEPTION_INSUFFICIENT_STORAGE = "Insufficient Storage"
        const val EXCEPTION_DOWNLOAD_FAILED = "Downloading Failed"

        @Volatile
        private var INSTANCE: DownloadStaticContent? = null

        @Synchronized
        fun getInstance(context: Context): DownloadStaticContent =
            INSTANCE ?: DownloadStaticContent(context).also { INSTANCE = it }
    }
}

