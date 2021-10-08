package com.roche.dis.systemmessages

import com.roche.dis.systemmessages.data.api.SystemMessagesApiService
import com.roche.dis.systemmessages.data.model.SystemMessage

object SystemMessages {

    suspend fun getSystemMessages(
        baseUrl: String,
        messageTypeList: List<String>,
        appOrSamdId: String,
        appOrSamdVersion: String,
        country: String? = null
    ): List<SystemMessage> {
        val url = baseUrl + SystemMessagesApiService.SYSTEM_MESSAGES_END_POINT
        val response = SystemMessagesApiService.getInstance().getSystemMessages(
            url = baseUrl,
            device = getDevice(),
            os = getOS(),
            osVersion = getOsVersion(),
            appOrSamdId = appOrSamdId,
            appOrSamdVersion = appOrSamdVersion,
            country = country
        )
        return response.systemMessagesList
    }

    private fun getDevice() = android.os.Build.MODEL
    private fun getOS() = "Android"
    private fun getOsVersion() = android.os.Build.VERSION.SDK_INT.toString()
}