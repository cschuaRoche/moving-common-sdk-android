package com.roche.roche.dis

import android.util.Log
import androidx.annotation.StringDef
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.suspendCoroutine


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

@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
@StringDef(
    Response.UPDATED,
    Response.ALREADY_UP_TO_DATE
)
annotation class Response {
    companion object {
        const val UPDATED = "Successfully updated"
        const val ALREADY_UP_TO_DATE = "Already up to date"
        const val EMPTY_JSON = "Empty JSON"
    }
}

class UserManualViewModel(): ViewModel() {
    companion object {
        const val EXCEPTION_NOT_MODIFIED = "Not Modified"
    }

    suspend fun syncUserManuals(configUrl: String, @LocaleType localeType: String): @Response String {
        return try {
            val jsonResponse = getStaticResponse(configUrl)
            suspendCoroutine { cont ->
                // get sharedPreference from etag
                // hardcode for now
                Log.d("cschua", "jsonResponse: $jsonResponse")
                cont.resumeWith(Result.success(Response.UPDATED))
                //cont.resumeWith(Result.failure(java.lang.Exception("errorMessage")))
            }
        } catch (e: IllegalStateException) {
            Response.ALREADY_UP_TO_DATE
        }
    }

    @Throws(IllegalStateException::class)
    suspend fun getStaticResponse(staticURL: String): String? {
        return withContext(Dispatchers.IO) {
            val url =
                URL(staticURL)
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

            // TODO get etag value from secure shared preference
            val etag = "8dc0c63a38126f59dadde2a309805d43"
            if (etag.isNotBlank()) {
                urlConnection.addRequestProperty("If-None-Match", etag)
                urlConnection.useCaches = false
            }

            //printHeaderKeys(urlConnection)

            if (HttpURLConnection.HTTP_NOT_MODIFIED == urlConnection.responseCode) {
                throw IllegalStateException(EXCEPTION_NOT_MODIFIED)
            } else {
                try {
                    val inputStream = BufferedReader(
                        InputStreamReader(
                            urlConnection.inputStream
                        )
                    )
                    readStream(inputStream)
                } catch (e: IOException) {
                    Log.d("http", "error: $e")
                    null
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

    private fun printHeaderKeys(urlConnection: HttpURLConnection) {
        for (key in urlConnection.headerFields.keys) {
            Log.d("http", "$key : ${urlConnection.headerFields[key]}")
        }
    }
}