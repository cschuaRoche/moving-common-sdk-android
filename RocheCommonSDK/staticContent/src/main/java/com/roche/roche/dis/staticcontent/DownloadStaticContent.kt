package com.roche.roche.dis.staticcontent

import android.content.Context
import android.util.Log
import androidx.annotation.StringDef
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            const val SV_FI = "sv-fi"
            const val DE_DE = "de-de"
            const val EN_AU = "en-au"
            const val EN_GB = "en-gb"
            const val EN_US = "en-us"
            const val FI_FI = "fi_fi"
            const val FR_FR = "fr_fr"
            const val IT_IT = "it_it"
            const val PT_PT = "pt_pt"
        }
    }

    private const val LOG_TAG = "DownloadStaticContent"
    private const val EXCEPTION_NOT_MODIFIED = "Not Modified"
    private const val EXCEPTION_APP_VERSION_NOT_FOUND = "Manifest App Version Not Found"
    private const val EXCEPTION_MANIFEST_LOCALE_NOT_FOUND = "Manifest Locale Not Found"
    private const val HEADER_KEY_ETAG = "ETag"

    /**
     * Get zip content url from the given manifest url.
     *
     * @param context Context
     * @param manifestUrl Url of the manifest file
     * @param appVersion Application version
     * @param locale Locale
     */
    @Throws(IllegalStateException::class, IllegalArgumentException::class, JSONException::class)
    suspend fun getUrlFromManifest(
        context: Context,
        manifestUrl: String,
        appVersion: String,
        @LocaleType locale: String
    ): String {
        return withContext(Dispatchers.IO) {
            val url = URL(manifestUrl)
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

            // get etag value from secure shared preference
            val prefKey = DownloadStaticContentSharedPref.PREF_KEY_ETAG_PREFIX + locale
            val etag = DownloadStaticContentSharedPref.getETag(context, prefKey)
            if (etag.isNotBlank()) {
                urlConnection.addRequestProperty("If-None-Match", etag)
                urlConnection.useCaches = false
            }

            if (HttpURLConnection.HTTP_NOT_MODIFIED == urlConnection.responseCode) {
                throw IllegalStateException(EXCEPTION_NOT_MODIFIED)
            } else {
                try {
                    val newETag = urlConnection.headerFields[HEADER_KEY_ETAG]
                    val inputStream = BufferedReader(
                        InputStreamReader(
                            urlConnection.inputStream
                        )
                    )
                    val jsonResponse = readStream(inputStream)
                    val zipContentUrl = parseManifestJson(jsonResponse, appVersion, locale)
                    // Cache new eTag to secured shared pref
                    if (newETag != null && newETag.isNotEmpty()) {
                        DownloadStaticContentSharedPref.saveETag(
                            context,
                            prefKey,
                            newETag[0]
                        )
                    }
                    zipContentUrl
                } catch (e: IOException) {
                    Log.d(LOG_TAG, "error: $e")
                    throw e
                } finally {
                    urlConnection.disconnect()
                }
            }
        }
    }

    private fun readStream(inputStream: BufferedReader): String {
        val sb = StringBuilder()
        var line: String?
        while (inputStream.readLine().also { line = it } != null) {
            sb.append(line)
            Log.i("readStream", line!!)
        }
        return sb.toString()
    }

    @Throws(IllegalArgumentException::class, JSONException::class)
    private fun parseManifestJson(
        jsonResponse: String,
        appVersion: String,
        @LocaleType locale: String
    ): String {
        return try {
            val jsonObject = JSONObject(jsonResponse)
            if (jsonObject.has(appVersion)) {
                val versionObj = jsonObject.getJSONObject(appVersion)
                if (versionObj.has(locale)) {
                    versionObj.getString(locale)
                } else {
                    Log.e(LOG_TAG, "Content not found for $locale locale")
                    throw IllegalArgumentException(EXCEPTION_MANIFEST_LOCALE_NOT_FOUND)
                }
            } else {
                Log.e(LOG_TAG, "Content not found for $appVersion version")
                throw IllegalArgumentException(EXCEPTION_APP_VERSION_NOT_FOUND)
            }
        } catch (e: JSONException) {
            Log.e(LOG_TAG, "error: $e")
            throw e
        }
    }

    private fun downloadContent(
        context: Context,
        contentUrl: String,
        callback: DownloadStaticContentCallback
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL(contentUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val fileLength = connection.contentLength

                // download the file
                val input: InputStream = BufferedInputStream(connection.inputStream)
                val fileName = contentUrl.substring(contentUrl.lastIndexOf("/") + 1)
                val path =
                    context.getExternalFilesDir(null)?.absoluteFile?.path + File.separator + fileName
                val output: OutputStream = FileOutputStream(path)

                val data = ByteArray(4096)
                var total: Long = 0
                var count: Int
                while (input.read(data).also { count = it } != -1) {
                    // publishing the progress....
                    total += count
                    if (fileLength > 0) { // only if total length is known
                        withContext(Dispatchers.Main) {
                            callback.publishProgress(((total * 100 / fileLength).toInt()))
                        }
                    }
                    output.write(data, 0, count)
                }

                // close streams
                output.flush()
                output.close()
                input.close()

                withContext(Dispatchers.Main) {
                    callback.success()
                }
            } catch (e: Exception) {
                Log.e(LOG_TAG, "Exception ${e.localizedMessage}")
                withContext(Dispatchers.Main) {
                    callback.failure(e.localizedMessage)
                }
            }
        }
    }
}

