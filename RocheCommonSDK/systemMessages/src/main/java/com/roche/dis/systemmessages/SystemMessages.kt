package com.roche.dis.systemmessages

import com.roche.dis.systemmessages.data.api.SystemMessagesApiService
import com.roche.dis.systemmessages.data.model.SystemMessage
import retrofit2.HttpException

object SystemMessages {

    @Throws(SystemMessagesException::class)
    suspend fun getSystemMessages(
        baseUrl: String,
        messageTypeList: List<String>,
        appOrSamdId: String,
        appOrSamdVersion: String,
        country: String? = null
    ): List<SystemMessage> {
        try {
            val url = baseUrl + SystemMessagesApiService.SYSTEM_MESSAGES_END_POINT
            val response = SystemMessagesApiService.getInstance().getSystemMessages(
                url = url,
                device = getDevice(),
                os = getOS(),
                osVersion = getOsVersion(),
                appOrSamdId = appOrSamdId,
                appOrSamdVersion = appOrSamdVersion,
                country = country
            )
            return response.systemMessagesList.filter { it.type in messageTypeList }
        } catch (e: Exception) {
            if (e is HttpException) {
                throw SystemMessagesException(e.code(), e)
            }
            throw e
        }
    }

    private fun getDevice() = android.os.Build.MODEL
    private fun getOS() = "Android"
    private fun getOsVersion() = android.os.Build.VERSION.SDK_INT.toString()
}