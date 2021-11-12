package com.roche.ssg.pushnotification.model

import kotlinx.serialization.Serializable

@Serializable
data class DeregisterResponse(
    val meta: Meta,
    val userId: String,
    val token: String
)