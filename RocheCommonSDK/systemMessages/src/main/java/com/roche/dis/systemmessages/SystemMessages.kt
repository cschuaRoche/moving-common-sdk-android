package com.roche.dis.systemmessages

import com.roche.dis.systemmessages.data.api.RetrofitApiService
import com.roche.dis.systemmessages.data.api.SystemMessagesApiService
import com.roche.dis.systemmessages.data.model.SystemMessage
import retrofit2.HttpException

object SystemMessages {

    /**
     * Get system messages based on message type, appOrSamdId, appOrSamdVersion and country
     *
     * @param baseUrl base url for getting the system messages
     * @param messageTypeList list of message types
     * @param appOrSamdId application or samd id
     * @param appOrSamdVersion application or samd version
     * @param country country (optional)
     */
    @Throws(RetrofitApiService.ApiException::class)
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
                throw RetrofitApiService.ApiException(e.code())
            }
            throw e
        }
    }

    private fun getDevice() = android.os.Build.MODEL
    private fun getOS() = "android"
    private fun getOsVersion() = android.os.Build.VERSION.SDK_INT.toString()
}