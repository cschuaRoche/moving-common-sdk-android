package com.roche.ssg.staticcontent

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

/**
 * A utility to download HTTP file
 */
object DownloadManagerUtil {
    /**
     * Downloads the file in the background
     *
     * @param context the application context
     * @param url HTTP Url which needs to be downloaded
     * @param downloadTo directory where file will be downloaded
     * @param allowWifiOnly true - downloads the file on WIFI only.
     * By default, it will download the file over WIFI or Mobile network
     * @param hideDownloadNotification true - Hides downloading notification.
     * By default, it will show the notification
     * @param progress callback to show the progress of the download
     *
     * @return returns downloaded file path
     */
    suspend fun download(
        context: Context,
        url: String,
        downloadTo: String,
        allowWifiOnly: Boolean = false,
        hideDownloadNotification: Boolean = false,
        progress: (Int) -> Unit
    ): String {
        return withContext(Dispatchers.IO) {
            val request = DownloadManager.Request(Uri.parse(url)).apply {
                if (allowWifiOnly) {
                    setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                } else {
                    setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                }
                if (hideDownloadNotification) {
                    setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                }
            }
            val downloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadId = downloadManager.enqueue(request)
            trackProgress(downloadId, downloadManager, progress)
            moveDownload(context, downloadId, downloadManager, downloadTo)
        }
    }

    /**
     * Tracks progress of the download
     *
     * @param downloadId Id of the download
     * @param downloadManager DownloadManager instance
     * @param progress callback to show the progress of the download
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal suspend fun trackProgress(
        downloadId: Long,
        downloadManager: DownloadManager,
        progress: (Int) -> Unit
    ) {
        var isDownloadFinished = false
        var currentProgress = -1
        while (!isDownloadFinished) {
            val cursor = downloadManager.query(DownloadManager.Query().setFilterById(downloadId))
            if (cursor.moveToFirst()) {
                when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                    DownloadManager.STATUS_FAILED -> {
                        throw IllegalStateException(DownloadStaticContent.EXCEPTION_DOWNLOAD_FAILED)
                    }
                    DownloadManager.STATUS_RUNNING -> {
                        val total =
                            cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                        if (total >= 0) {
                            val downloaded =
                                cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                            val newProgress = (downloaded * 100L / total).toInt()
                            if (currentProgress != newProgress) {
                                currentProgress = newProgress
                                withContext(Dispatchers.Main) {
                                    progress(currentProgress)
                                }
                            }
                        }
                    }
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        progress(100)
                        isDownloadFinished = true
                    }
                }
            }
            cursor.close()
        }
    }

    /**
     * Moves download to the destination directory
     *
     * @param context the application context
     * @param downloadId Id of the download
     * @param downloadManager DownloadManager instance
     * @param destPath directory where downloaded file will be moved
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun moveDownload(
        context: Context,
        downloadId: Long,
        downloadManager: DownloadManager,
        destPath: String
    ): String {
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
        return destPath
    }
}