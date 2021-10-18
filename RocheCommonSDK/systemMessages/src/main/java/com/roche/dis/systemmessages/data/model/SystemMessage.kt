package com.roche.dis.systemmessages.data.model

data class SystemMessage(
    val defaultMessage: String,
    val effectiveFrom: Long,
    val effectiveTo: Long,
    val resourceId: String,
    val translationKey: String,
    val type: String
)