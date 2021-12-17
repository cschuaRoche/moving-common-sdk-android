package com.roche.ssg.pushnotification.model

import kotlinx.serialization.Serializable

@Serializable
internal data class RegisterRequest(
    val userId: String,
    val deviceToken: String,
    val os: String,
    val channelType: String,
    val deviceInfo: DeviceInfo,
    val metadata: Metadata
)

@Serializable
data class DeviceInfo(
    val osVersion: String?,
    val model: String?,
    val make: String?,
    val appVersion: String?
)

@Serializable
internal data class Metadata(val country: String, val orgId: String, val hcpId: String)
