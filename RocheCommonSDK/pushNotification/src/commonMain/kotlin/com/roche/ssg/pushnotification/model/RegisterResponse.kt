package com.roche.ssg.pushnotification.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val meta: Meta,
    val endpointId:String,
    val userId: String,
    val deviceToken: String,
    val os: String,
    val deviceInfo: DeviceInfo,
)

@Serializable
data class Meta(val requestTime: String, val transactionId: String)
