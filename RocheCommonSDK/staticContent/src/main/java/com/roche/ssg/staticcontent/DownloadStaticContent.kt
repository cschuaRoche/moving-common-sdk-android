package com.roche.ssg.staticcontent

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.annotation.StringDef
import androidx.annotation.VisibleForTesting
import com.roche.ssg.staticcontent.entity.ManifestInfo
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
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException

object DownloadStaticContent {
    @Retention(AnnotationRetention.SOURCE)
    @StringDef(
        LocaleType.SV_FI,
        LocaleType.DE_DE,
        LocaleType.EN_AU,
        LocaleType.EN_GB,
        LocaleType.EN_US,
        LocaleType.FI_FI,
        LocaleType.FR_FR,
        LocaleType.IT_IT,
        LocaleType.PT_PT
    )
    annotation class LocaleType {
        companion object {
            const val SV_FI = "sv_FI"
            const val DE_DE = "de_DE"
            const val EN_AU = "en_AU"
            const val EN_GB = "en_GB"
            const val EN_US = "en_US"
            const val FI_FI = "fi_FI"
            const val FR_FR = "fr_FR"
            const val IT_IT = "it_IT"
            const val PT_PT = "pt_PT"
        }
    }

    private const val LOG_TAG = "DownloadStaticContent"
    private const val ZIPPED_FILE_EXTENSION = ".zip"
    private const val HEADER_KEY_ETAG = "ETag"
    private const val JSON_KEY_PATH = "path"
    private const val JSON_KEY_FILE_SIZE = "fileSize"
    private const val JSON_KEY_ORIGINAL_SIZE = "originalSize"

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

    /**
     * Download static assets and unzips them of given app version, locale and file key.
     * Deletes older app version's content if newer app version's data is requested.
     *
     * @param context application context
     * @param manifestUrl url of the manifest file
     * @param appVersion application version
     * @param locale locale for which static assets needs to be downloaded
     * @param fileKey file key for which static assets needs to be downloaded (e.g. user_manual)
     * @param progress callback which will return the progress of the download
     * @param targetSubDir sub directory where the files will be downloaded to
     * @param allowWifiOnly download asset on WIFI only (Default is false).
     *
     * @return downloaded and unzipped static asset's path
     */
    @Throws(
        IllegalStateException::class,
        IllegalArgumentException::class,
        IOException::class,
        JSONException::class,
        UnknownHostException::class
    )
    suspend fun downloadStaticAssets(
        context: Context,
        manifestUrl: String,
        appVersion: String,
        @LocaleType locale: String,
        fileKey: String,
        progress: (Int) -> Unit,
        targetSubDir: String,
        allowWifiOnly: Boolean = false
    ): String {
        try {
            // target sub directory should not be empty
            if (targetSubDir.isBlank()) {
                throw IllegalArgumentException(EXCEPTION_TARGET_SUB_DIRECTORY_EMPTY)
            }
            // read manifest file and get the url
            val manifestInfo = getInfoFromManifest(
                context,
                manifestUrl,
                appVersion,
                locale,
                fileKey,
                targetSubDir,
                allowWifiOnly
            )

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
            val subDirPath = targetSubDir + File.separator + appVersion
            val zipPath =
                downloadFromUrl(context, manifestInfo.path, progress, subDirPath, allowWifiOnly)

            // check free space to unzip the file
            if (context.filesDir.usableSpace <= manifestInfo.originalSize) {
                throw IllegalStateException(EXCEPTION_INSUFFICIENT_STORAGE)
            }

            // unzip the file and save the path to shared pref
            val unzipPath = unzipFile(context, zipPath, subDirPath)
            DownloadStaticContentSharedPref.setFilePath(
                context,
                targetSubDir,
                appVersion,
                locale,
                fileKey,
                unzipPath
            )
            // Delete zipped file
            File(zipPath).delete()
            // check and delete old version
            checkAndDeleteOldVersionData(context, targetSubDir, appVersion)
            return unzipPath
        } catch (e: Exception) {
            if (e.message == EXCEPTION_NOT_MODIFIED) {
                // return existing file path as manifest is not modified
                return DownloadStaticContentSharedPref.getFilePath(
                    context,
                    targetSubDir,
                    appVersion,
                    locale,
                    fileKey
                )
            }
            throw e
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
    suspend fun getInfoFromManifest(
        context: Context,
        manifestUrl: String,
        appVersion: String,
        @LocaleType locale: String,
        fileKey: String,
        targetSubDir: String,
        allowWifiOnly: Boolean = false
    ): ManifestInfo {
        checkConnection(context, allowWifiOnly)
        return withContext(Dispatchers.IO) {
            var urlConnection: HttpURLConnection? = null
            try {
                urlConnection = getUrlConnection(manifestUrl)

                // get etag and downloaded file path value from secure shared preference
                val etag = DownloadStaticContentSharedPref.getETag(
                    context,
                    targetSubDir,
                    appVersion,
                    locale,
                    fileKey
                )
                val filePath = DownloadStaticContentSharedPref.getFilePath(
                    context,
                    targetSubDir,
                    appVersion,
                    locale,
                    fileKey
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
                    val manifestInfo = parseManifest(jsonResponse, appVersion, locale, fileKey)
                    // Cache new eTag to secured shared pref
                    if (newETag != null && newETag.isNotEmpty()) {
                        DownloadStaticContentSharedPref.setETag(
                            context,
                            targetSubDir,
                            appVersion,
                            locale,
                            fileKey,
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
     * @param fileURL the file url
     * @param progress callback which will return the progress of the download
     * @param targetSubDir sub directory where the files will be downloaded to
     * @param allowWifiOnly download asset on WIFI only (Default is false).
     *
     * @return downloaded zipped file's path
     */
    @Throws(IOException::class, IllegalStateException::class)
    suspend fun downloadFromUrl(
        context: Context,
        fileURL: String,
        progress: (Int) -> Unit,
        targetSubDir: String,
        allowWifiOnly: Boolean = false
    ): String {
        checkConnection(context, allowWifiOnly)
        return withContext(Dispatchers.IO) {
            val fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1)
            val file = File(context.filesDir.toString() + File.separator + targetSubDir)
            if (file.exists().not()) {
                file.mkdirs()
            }
            val path = file.path + File.separator + fileName

            // download the file
            val request = DownloadManager.Request(Uri.parse(fileURL))
            if (allowWifiOnly) {
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            } else {
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            }
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            val downloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadId = downloadManager.enqueue(request)
            val isDownloadSuccessful = trackProgress(downloadId, downloadManager, progress)
            if (!isDownloadSuccessful) {
                throw IllegalStateException(EXCEPTION_DOWNLOAD_FAILED)
            }
            moveDownloadToInternalDir(downloadId, downloadManager, path, context)
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
        targetSubDir: String
    ): String {
        val unzipPath = UnZipUtils.unzipFromAppFiles(filePath, context, targetSubDir)
        if (unzipPath == null) {
            Log.e(LOG_TAG, "error occurred in unzipping the file")
            throw IllegalStateException(EXCEPTION_UNZIPPING_FILE)
        }
        return unzipPath
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
            Log.i("readStream", line!!)
        }
        return sb.toString()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal suspend fun trackProgress(
        downloadId: Long,
        downloadManager: DownloadManager,
        progress: (Int) -> Unit
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
                                progress((downloaded * 100L / total).toInt())
                            }
                        }
                    }
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        progress(100)
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
        downloadId: Long,
        downloadManager: DownloadManager,
        destPath: String,
        context: Context
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

    @Throws(IllegalArgumentException::class, JSONException::class)
    private fun parseManifest(
        jsonResponse: String,
        appVersion: String,
        @LocaleType locale: String,
        fileKey: String
    ): ManifestInfo {
        return try {
            val jsonObject = JSONObject(jsonResponse)
            if (jsonObject.has(appVersion)) {
                val versionObj = jsonObject.getJSONObject(appVersion)
                if (versionObj.has(locale)) {
                    val localeObj = versionObj.getJSONObject(locale)
                    if (localeObj.has(fileKey)) {
                        val fileObj = localeObj.getJSONObject(fileKey)
                        ManifestInfo(
                            fileObj.getString(JSON_KEY_PATH),
                            fileObj.getLong(JSON_KEY_FILE_SIZE),
                            fileObj.getLong(JSON_KEY_ORIGINAL_SIZE)
                        )
                    } else {
                        Log.e(LOG_TAG, "Content not found for $fileKey key")
                        throw IllegalArgumentException(EXCEPTION_MANIFEST_FILE_KEY_NOT_FOUND)
                    }
                } else {
                    Log.e(LOG_TAG, "Content not found for $locale locale")
                    throw IllegalArgumentException(EXCEPTION_MANIFEST_LOCALE_NOT_FOUND)
                }
            } else {
                Log.e(LOG_TAG, "Content not found for $appVersion version")
                throw IllegalArgumentException(EXCEPTION_MANIFEST_APP_VERSION_NOT_FOUND)
            }
        } catch (e: JSONException) {
            Log.e(LOG_TAG, "error: $e")
            throw e
        }
    }

    @Throws(IllegalStateException::class)
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
}

