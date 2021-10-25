package com.roche.ssg.sample.pushnotification.model

import kotlinx.serialization.Serializable

@Serializable
data class DeregisterRequest(
    val userId: String,
    val token: String
)
