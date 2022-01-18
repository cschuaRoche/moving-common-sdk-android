package com.roche.ssg.staticcontent.entity

data class StaticContentInfo(
    val manifestUrl: String,
    val appVersion: String,
    val locale: String,
    val fileKey: String,
    val targetSubDir: String,
    val allowWifiOnly: Boolean = false
)