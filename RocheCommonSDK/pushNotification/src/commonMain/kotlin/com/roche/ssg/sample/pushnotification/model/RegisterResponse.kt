package com.roche.ssg.sample.pushnotification.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val meta: Meta,
    val resourceId: String,
    val userId: String,
    val token: String,
    val os: String,
    val osVersion: String,
    val model: String,
    val make: String,
    val appVersion: String,
    val country: String
)

@Serializable
data class Meta(val transactionId: String, val requestTime: Long)
