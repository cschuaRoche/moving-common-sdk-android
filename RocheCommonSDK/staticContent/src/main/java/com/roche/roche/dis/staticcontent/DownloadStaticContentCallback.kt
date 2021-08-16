package com.roche.roche.dis.staticcontent

interface DownloadStaticContentCallback {
    fun success()
    fun failure(errorMessage: String)
    fun publishProgress(progress: Int)
}