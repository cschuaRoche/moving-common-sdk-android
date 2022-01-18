package com.roche.ssg.staticcontent

import com.roche.ssg.staticcontent.entity.StaticContentInfo

sealed class DownloadStaticContentResult(val staticContentInfo: StaticContentInfo) {
    class Success(staticContentInfo: StaticContentInfo, val path: String) :
        DownloadStaticContentResult(staticContentInfo)

    class Failure(staticContentInfo: StaticContentInfo, val message: String) :
        DownloadStaticContentResult(staticContentInfo)

    class DownloadProgress(staticContentInfo: StaticContentInfo, val progress: Int) :
        DownloadStaticContentResult(staticContentInfo)
}