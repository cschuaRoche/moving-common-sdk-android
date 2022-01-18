package com.roche.ssg.etlpipeline.model

import kotlinx.serialization.Serializable

@Serializable
data class SignedUrlResponse(val url: String, val encryptionKeyId: String)
