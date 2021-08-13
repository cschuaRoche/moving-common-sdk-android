package com.roche.roche.dis.staticcontent

import android.util.Log
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

object DownloadStaticContent {
    fun downloadToFileSystem(url: String) {
        try {
            val url = URL(url)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            // download the file
            val input: InputStream = BufferedInputStream(connection.inputStream)

            val path = "/sdcard/test.json"
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
        } catch (e: Exception) {
            Log.e("DownloadStaticContent", "Exception ${e.localizedMessage}")
        }
    }
}