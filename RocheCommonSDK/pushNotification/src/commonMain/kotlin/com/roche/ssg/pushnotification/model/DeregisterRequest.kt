package com.roche.ssg.pushnotification.model

import kotlinx.serialization.Serializable

@Serializable
data class DeregisterRequest(
    val userId: String,
    val token: String
)
