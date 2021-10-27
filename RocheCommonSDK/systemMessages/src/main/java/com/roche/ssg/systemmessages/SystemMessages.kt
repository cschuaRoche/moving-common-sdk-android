package com.roche.ssg.systemmessages

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.roche.ssg.systemmessages.data.api.RetrofitApiService
import com.roche.ssg.systemmessages.data.api.SystemMessagesApiService
import com.roche.ssg.systemmessages.data.model.SystemMessage
import com.roche.ssg.systemmessages.data.model.SystemMessagesResponse
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
        context: Context,
        baseUrl: String,
        messageTypeList: List<String>,
        appOrSamdId: String,
        appOrSamdVersion: String,
        country: String? = null
    ): List<SystemMessage> {
        try {
            val url = baseUrl + SystemMessagesApiService.SYSTEM_MESSAGES_END_POINT
            val response = fetchSystemMessages(url, appOrSamdId, appOrSamdVersion, country)
            val dismissedMessages = clearExpiredMessages(context, response.systemMessagesList)
            return response.systemMessagesList.filter { (it.type in messageTypeList) && (it.resourceId !in dismissedMessages) }
        } catch (e: Exception) {
            if (e is HttpException) {
                throw RetrofitApiService.ApiException(e.code())
            }
            throw e
        }
    }

    /**
     * Caches resource id of the dismissed system message in shared pref
     *
     * @param context application context
     * @param resourceId resource id of the system message
     */
    fun dismissMessage(context: Context, resourceId: String) {
        val dismissedMessages = SystemMessagesSharedPref.getDismissedMessages(context)
        if (dismissedMessages.contains(resourceId).not()) {
            dismissedMessages.add(resourceId)
            SystemMessagesSharedPref.setDismissedMessages(context, dismissedMessages)
        }
    }

    /**
     * clear expired messages from the cache
     */
    private fun clearExpiredMessages(
        context: Context,
        systemMessages: List<SystemMessage>
    ): HashSet<String> {
        val dismissedMessages = SystemMessagesSharedPref.getDismissedMessages(context)
        if (dismissedMessages.isNotEmpty()) {
            val resIdList = systemMessages.map { it.resourceId }
            dismissedMessages.removeAll { it !in resIdList }
            SystemMessagesSharedPref.setDismissedMessages(context, dismissedMessages)
        }
        return dismissedMessages
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal suspend fun fetchSystemMessages(
        url: String,
        appOrSamdId: String,
        appOrSamdVersion: String,
        country: String?
    ): SystemMessagesResponse {
        return SystemMessagesApiService.getInstance().getSystemMessages(
            url = url,
            device = getDevice(),
            os = getOS(),
            osVersion = getOsVersion(),
            appOrSamdId = appOrSamdId,
            appOrSamdVersion = appOrSamdVersion,
            country = country
        )
    }

    private fun getDevice() = android.os.Build.MODEL
    private fun getOS() = "android"
    private fun getOsVersion() = android.os.Build.VERSION.SDK_INT.toString()
}