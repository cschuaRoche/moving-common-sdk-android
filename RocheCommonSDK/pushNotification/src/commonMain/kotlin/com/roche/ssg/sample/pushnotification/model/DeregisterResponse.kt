package com.roche.ssg.sample.pushnotification.model

import kotlinx.serialization.Serializable

@Serializable
data class DeregisterResponse(
    val meta: Meta,
    val userId: String,
    val token: String
)
