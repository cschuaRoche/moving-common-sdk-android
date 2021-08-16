package com.roche.roche.dis.staticcontent

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

object DownloadStaticContent {
    fun downloadToFileSystem(context: Context, urlString: String, fileName:String, callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                // download the file
                val input: InputStream = BufferedInputStream(connection.inputStream)
                val path = context.getExternalFilesDir(null)?.absoluteFile?.path + File.separator + fileName
                val output: OutputStream = FileOutputStream(path)

                val data = ByteArray(1024)
                var count: Int
                while (input.read(data).also { count = it } != -1) {
                    output.write(data, 0, count)
                }

                // close streams
                output.flush()
                output.close()
                input.close()

                withContext(Dispatchers.Main) {
                    callback(true)
                }
            } catch (e: Exception) {
                Log.e("DownloadStaticContent", "Exception ${e.localizedMessage}")
                withContext(Dispatchers.Main) {
                    callback(false)
                }
            }
        }
    }
}