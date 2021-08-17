package com.roche.roche.dis.staticcontent

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

object DownloadStaticContent {
    private const val LOG_TAG = "DownloadStaticContent"

    /**
     * Download static content to file system (Location: Android/data/<package-name>/files)
     * for the given version
     *
     * @param context Context
     * @param url S3 URL which has the information of static content
     * @param version Download the content for the given version number
     * @param callback Callback method
     */
    fun downloadToFileSystem(
        context: Context,
        url: String,
        version: String,
        callback: DownloadStaticContentCallback
    ) {
        DownloadStaticContentApiService.getInstance().fetchStaticContentInfo(url)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        responseBody?.let {
                            val jsonResponse = JSONObject(it.string())
                            if (jsonResponse.has(version)) {
                                val versionJson = jsonResponse.getJSONObject(version)
                                val zipContentUrl = versionJson.getString("en-us")
                                Log.d(LOG_TAG, "Zip content url $zipContentUrl")
                                downloadContent(context, zipContentUrl, callback)
                            } else {
                                Log.d(LOG_TAG, "Content not found for $version version")
                                callback.failure("Content not found for $version version")
                            }
                        }
                    } else {
                        Log.d(LOG_TAG, "Response failed with ${response.code()} error")
                        callback.failure("Response failed with ${response.code()} error")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(LOG_TAG, "Response failed ${t.localizedMessage}")
                    callback.failure(t.localizedMessage)
                }
            })
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

