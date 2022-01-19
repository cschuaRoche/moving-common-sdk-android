package com.roche.ssg.staticcontent.entity

import com.roche.ssg.staticcontent.DownloadStaticContentResult

internal data class StaticContentTask(
    val staticContentInfo: StaticContentInfo,
    val result: (DownloadStaticContentResult) -> Unit
)