package com.roche.ssg.staticcontent.entity

data class ManifestInfo(
    val path: String,
    val fileSize: Long,
    val originalSize: Long
)