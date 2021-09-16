package com.roche.apprecall

import kotlinx.serialization.Serializable

@Serializable
class AppRecallResponse(
    val updateAvailable: Boolean,
    val updateRequired: Boolean,
    val recall: Boolean
)