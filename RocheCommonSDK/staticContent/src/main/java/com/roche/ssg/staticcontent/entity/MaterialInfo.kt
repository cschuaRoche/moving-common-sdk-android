package com.roche.ssg.staticcontent.entity

import com.roche.ssg.staticcontent.DownloadStaticContentResult

internal data class MaterialInfo(
    val staticContentInfo: StaticContentInfo,
    val result: (DownloadStaticContentResult) -> Unit
) {
    val prefKey: String by lazy {
        with(staticContentInfo) {
            "${targetSubDir}_${appVersion.replace(".", "_")}_${locale}_${fileKey}"
        }
    }
}