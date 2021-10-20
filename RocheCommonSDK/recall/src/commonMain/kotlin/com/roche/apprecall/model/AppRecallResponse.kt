package com.roche.apprecall.model

import kotlinx.serialization.Serializable

@Serializable
data class AppRecallResponse(
    val updateAvailable: Boolean,
    val updateRequired: Boolean,
    val recall: Boolean
)