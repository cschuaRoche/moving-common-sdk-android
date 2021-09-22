package com.roche.roche.dis.staticcontent

import android.content.Context
import android.util.Log
import androidx.annotation.StringDef
import androidx.annotation.VisibleForTesting
import com.roche.roche.dis.utils.NetworkUtils
import com.roche.roche.dis.utils.UnZipUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
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

    const val EXCEPTION_WIFI_NOT_AVAILABLE = "Wifi Not Available"
    const val EXCEPTION_NETWORK_NOT_AVAILABLE = "Network Not Available"
    const val EXCEPTION_NOT_MODIFIED = "Not Modified"
    const val EXCEPTION_INVALID_MANIFEST_FILE_FORMAT = "Invalid Manifest File Format"
    const val EXCEPTION_MANIFEST_APP_VERSION_NOT_FOUND = "Manifest App Version Not Found"
    const val EXCEPTION_MANIFEST_LOCALE_NOT_FOUND = "Manifest Locale Not Found"
    const val EXCEPTION_MANIFEST_FILE_KEY_NOT_FOUND = "Manifest File Key Not Found"
    const val EXCEPTION_UNZIPPING_FILE = "Error In Unzipping The File"

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
     * @param targetSubDir optional sub directory where the files will be downloaded to
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
        targetSubDir: String? = null,
        allowWifiOnly: Boolean = false
    ): String {
        try {
            // read manifest file and get the url
            val fileUrl =
                getUrlFromManifest(context, manifestUrl, appVersion, locale, fileKey, allowWifiOnly)

            // check if the url is not a zip file type then throw exception
            val fileExtension = fileUrl.substring(fileUrl.lastIndexOf("."))
            if (fileExtension.equals(ZIPPED_FILE_EXTENSION, true).not()) {
                throw IllegalStateException(EXCEPTION_INVALID_MANIFEST_FILE_FORMAT)
            }
            val subDirPath = if (targetSubDir != null) {
                appVersion + File.separator + targetSubDir
            } else {
                appVersion
            }

            // download the file
            val zipPath =
                downloadFromUrl(context, fileUrl, progress, subDirPath, allowWifiOnly)
            // unzip the file
            val unzipPath = unzipFile(context, zipPath, subDirPath)
            // save unzipping file path to shared pref
            DownloadStaticContentSharedPref.setFilePath(
                context,
                appVersion,
                locale,
                fileKey,
                unzipPath
            )
            // Delete zipped file
            File(zipPath).delete()
            // check and delete old version
            checkAndDeleteOldVersionData(context, appVersion)
            return unzipPath
        } catch (e: Exception) {
            if (e.message == EXCEPTION_NOT_MODIFIED) {
                // return existing file path as manifest is not modified
                return DownloadStaticContentSharedPref.getFilePath(
                    context,
                    appVersion,
                    locale,
                    fileKey
                )
            }
            throw e
        }
    }

    /**
     * Retrieve a file URL based on the app version, locale and file key from given manifest
     *
     * @param context Context
     * @param manifestUrl Url of the manifest file
     * @param appVersion Application version
     * @param locale Locale
     * @param fileKey file key for which static assets needs to be downloaded (e.g. user_manual)
     * @param allowWifiOnly download asset on WIFI only (Default is false).
     */
    @Throws(
        IllegalStateException::class,
        IllegalArgumentException::class,
        IOException::class,
        JSONException::class
    )
    suspend fun getUrlFromManifest(
        context: Context,
        manifestUrl: String,
        appVersion: String,
        @LocaleType locale: String,
        fileKey: String,
        allowWifiOnly: Boolean = false
    ): String {
        checkConnection(context, allowWifiOnly)
        return withContext(Dispatchers.IO) {
            var urlConnection: HttpURLConnection? = null
            try {
                urlConnection = getUrlConnection(manifestUrl)

                // get etag and downloaded file path value from secure shared preference
                val etag =
                    DownloadStaticContentSharedPref.getETag(context, appVersion, locale, fileKey)
                val filePath = DownloadStaticContentSharedPref.getFilePath(
                    context,
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
                    val zipContentUrl = parseManifest(jsonResponse, appVersion, locale, fileKey)
                    // Cache new eTag to secured shared pref
                    if (newETag != null && newETag.isNotEmpty()) {
                        DownloadStaticContentSharedPref.setETag(
                            context,
                            appVersion,
                            locale,
                            fileKey,
                            newETag[0]
                        )
                    }
                    zipContentUrl
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
     * @param targetSubDir optional sub directory where the files will be downloaded to
     * @param allowWifiOnly download asset on WIFI only (Default is false).
     *
     * @return downloaded zipped file's path
     */
    @Throws(IOException::class)
    suspend fun downloadFromUrl(
        context: Context,
        fileURL: String,
        progress: (Int) -> Unit,
        targetSubDir: String? = null,
        allowWifiOnly: Boolean = false
    ): String {
        checkConnection(context, allowWifiOnly)
        return withContext(Dispatchers.IO) {
            val connection = getUrlConnection(fileURL)
            connection.connect()

            val fileLength = connection.contentLength
            val fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1)
            val absoluteFilePath = context.filesDir.toString()
            val path = if (targetSubDir != null) {
                val file = File(absoluteFilePath + File.separator + targetSubDir)
                if (file.exists().not()) {
                    file.mkdirs()
                }
                file.path + File.separator + fileName
            } else {
                absoluteFilePath + File.separator + fileName
            }

            // download the file
            val input: InputStream = BufferedInputStream(connection.inputStream)
            try {
                writeStream(input, path, fileLength, progress)
                path
            } catch (e: IOException) {
                Log.e(LOG_TAG, "Exception ${e.localizedMessage}")
                throw e
            } finally {
                // close streams
                input.close()
                connection.disconnect()
            }
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
        targetSubDir: String? = null
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
    internal suspend fun writeStream(
        input: InputStream,
        path: String,
        fileLength: Int,
        progress: (Int) -> Unit
    ) {
        val output: OutputStream = FileOutputStream(path)
        try {
            val data = ByteArray(4096)
            var total: Long = 0
            var count: Int
            while (input.read(data).also { count = it } != -1) {
                // publishing the progress....
                total += count
                if (fileLength > 0) { // only if total length is known
                    withContext(Dispatchers.Main) {
                        progress(((total * 100 / fileLength).toInt()))
                    }
                }
                output.write(data, 0, count)
            }
        } catch (e: IOException) {
            throw e
        } finally {
            output.flush()
            output.close()
        }
    }

    @Throws(IllegalArgumentException::class, JSONException::class)
    private fun parseManifest(
        jsonResponse: String,
        appVersion: String,
        @LocaleType locale: String,
        fileKey: String
    ): String {
        return try {
            val jsonObject = JSONObject(jsonResponse)
            if (jsonObject.has(appVersion)) {
                val versionObj = jsonObject.getJSONObject(appVersion)
                if (versionObj.has(locale)) {
                    val localeObj = versionObj.getJSONObject(locale)
                    if (localeObj.has(fileKey)) {
                        val fileObj = localeObj.getJSONObject(fileKey)
                        fileObj.getString(JSON_KEY_PATH)
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
    internal fun checkAndDeleteOldVersionData(context: Context, appVersion: String) {
        val existingVersion = DownloadStaticContentSharedPref.getVersion(context)
        if (existingVersion != appVersion) {
            if (existingVersion.isNotBlank()) {
                // delete old version's data
                File(context.filesDir.toString() + File.separator + existingVersion).deleteRecursively()
                // remove shared pref data
                DownloadStaticContentSharedPref.removeAllKeysOfAppVersion(context, existingVersion)
            }
            // update new version
            DownloadStaticContentSharedPref.setVersion(context, appVersion)
        }
    }
}

