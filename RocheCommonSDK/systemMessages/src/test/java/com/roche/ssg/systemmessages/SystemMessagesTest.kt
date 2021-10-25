package com.roche.ssg.systemmessages

import android.app.Application
import com.roche.ssg.systemmessages.data.api.RetrofitApiService
import com.roche.ssg.systemmessages.data.api.SystemMessagesApiService
import com.roche.ssg.systemmessages.data.model.Meta
import com.roche.ssg.systemmessages.data.model.SystemMessage
import com.roche.ssg.systemmessages.data.model.SystemMessagesResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class SystemMessagesTest : BaseMockkTest() {
    @MockK(relaxed = true)
    private lateinit var appContext: Application

    @Before
    override fun setup() {
        super.setup()
        mockkObject(SystemMessages)
        mockkObject(SystemMessagesSharedPref)
    }

    @Test
    fun `when getSystemMessages is successful then returns list of system messages`() =
        runBlocking {
            coEvery {
                SystemMessages.fetchSystemMessages(
                    BASE_URL + SystemMessagesApiService.SYSTEM_MESSAGES_END_POINT,
                    APP_SAMD_ID,
                    APP_SAMD_VERSION,
                    null
                )
            } returns getSystemMessageResponse()
            every { SystemMessagesSharedPref.getDismissedMessages(appContext) } returns hashSetOf()

            val messages = SystemMessages.getSystemMessages(
                appContext,
                BASE_URL,
                mutableListOf(MESSAGE_TYPE_QUALITY, MESSAGE_TYPE_ALERT),
                APP_SAMD_ID,
                APP_SAMD_VERSION
            )
            coVerify(exactly = 1) {
                SystemMessages.fetchSystemMessages(
                    BASE_URL + SystemMessagesApiService.SYSTEM_MESSAGES_END_POINT,
                    APP_SAMD_ID,
                    APP_SAMD_VERSION,
                    null
                )
            }
            verify(exactly = 1) { SystemMessagesSharedPref.getDismissedMessages(appContext) }
            Assert.assertEquals(2, messages.size)
            Assert.assertEquals(getQualitySystemMessage(), messages[0])
            Assert.assertEquals(getAlertSystemMessage(), messages[1])
        }

    @Test
    fun `getSystemMessages doesn't return dismissed message`() =
        runBlocking {
            coEvery {
                SystemMessages.fetchSystemMessages(
                    BASE_URL + SystemMessagesApiService.SYSTEM_MESSAGES_END_POINT,
                    APP_SAMD_ID,
                    APP_SAMD_VERSION,
                    null
                )
            } returns getSystemMessageResponse()
            every { SystemMessagesSharedPref.getDismissedMessages(appContext) } returns hashSetOf(
                ALERT_RES_ID
            )

            val messages = SystemMessages.getSystemMessages(
                appContext,
                BASE_URL,
                mutableListOf(MESSAGE_TYPE_QUALITY, MESSAGE_TYPE_ALERT),
                APP_SAMD_ID,
                APP_SAMD_VERSION
            )
            coVerify(exactly = 1) {
                SystemMessages.fetchSystemMessages(
                    BASE_URL + SystemMessagesApiService.SYSTEM_MESSAGES_END_POINT,
                    APP_SAMD_ID,
                    APP_SAMD_VERSION,
                    null
                )
            }
            verify(exactly = 1) { SystemMessagesSharedPref.getDismissedMessages(appContext) }
            Assert.assertEquals(1, messages.size)
            Assert.assertEquals(getQualitySystemMessage(), messages[0])
        }

    @Test
    fun `getSystemMessages filters alert system messages based on message type`() =
        runBlocking {
            coEvery {
                SystemMessages.fetchSystemMessages(
                    BASE_URL + SystemMessagesApiService.SYSTEM_MESSAGES_END_POINT,
                    APP_SAMD_ID,
                    APP_SAMD_VERSION,
                    null
                )
            } returns getSystemMessageResponse()
            every { SystemMessagesSharedPref.getDismissedMessages(appContext) } returns hashSetOf()

            val messages = SystemMessages.getSystemMessages(
                appContext,
                BASE_URL,
                mutableListOf(MESSAGE_TYPE_ALERT),
                APP_SAMD_ID,
                APP_SAMD_VERSION
            )
            coVerify(exactly = 1) {
                SystemMessages.fetchSystemMessages(
                    BASE_URL + SystemMessagesApiService.SYSTEM_MESSAGES_END_POINT,
                    APP_SAMD_ID,
                    APP_SAMD_VERSION,
                    null
                )
            }
            verify(exactly = 1) { SystemMessagesSharedPref.getDismissedMessages(appContext) }
            Assert.assertEquals(1, messages.size)
            Assert.assertEquals(getAlertSystemMessage(), messages[0])
        }

    @Test
    fun `getSystemMessages throws ApiException when HttpException occurs`() =
        runBlocking {
            val error = Response.error<String>(
                RetrofitApiService.APIResponseCode.NOT_FOUND,
                ResponseBody.create(MediaType.parse("text/plain"), "Test Server Error")
            )
            coEvery {
                SystemMessages.fetchSystemMessages(
                    BASE_URL + SystemMessagesApiService.SYSTEM_MESSAGES_END_POINT,
                    APP_SAMD_ID,
                    APP_SAMD_VERSION,
                    null
                )
            } throws HttpException(error)

            try {
                SystemMessages.getSystemMessages(
                    appContext,
                    BASE_URL,
                    mutableListOf(MESSAGE_TYPE_ALERT),
                    APP_SAMD_ID,
                    APP_SAMD_VERSION
                )
                Assert.fail("getSystemMessages should throw ApiException when HttpException occurs")
            } catch (e: RetrofitApiService.ApiException) {
                Assert.assertEquals(RetrofitApiService.APIResponseCode.NOT_FOUND, e.statusCode)
            }
            verify(exactly = 0) { SystemMessagesSharedPref.getDismissedMessages(appContext) }
        }

    @Test
    fun `dismissMessage should cache the resource id`() {
        val dismissedResId = "dismissedResId"
        every { SystemMessagesSharedPref.getDismissedMessages(appContext) } returns hashSetOf()
        every {
            SystemMessagesSharedPref.setDismissedMessages(
                appContext,
                hashSetOf(dismissedResId)
            )
        } returns Unit
        SystemMessages.dismissMessage(appContext, dismissedResId)
        verify(exactly = 1) { SystemMessagesSharedPref.getDismissedMessages(appContext) }
        verify(exactly = 1) {
            SystemMessagesSharedPref.setDismissedMessages(
                appContext,
                hashSetOf(dismissedResId)
            )
        }
    }

    @Test
    fun `dismissMessage shouldn't cache the resource id if it is already in shared pref`() {
        val dismissedResId = "dismissedResId"
        val dismissedMessages = hashSetOf(dismissedResId)
        every { SystemMessagesSharedPref.getDismissedMessages(appContext) } returns dismissedMessages
        SystemMessages.dismissMessage(appContext, dismissedResId)
        verify(exactly = 1) { SystemMessagesSharedPref.getDismissedMessages(appContext) }
        verify(exactly = 0) {
            SystemMessagesSharedPref.setDismissedMessages(appContext, dismissedMessages)
        }
    }

    private fun getSystemMessageResponse() = SystemMessagesResponse(
        getMeta(),
        mutableListOf(getQualitySystemMessage(), getAlertSystemMessage())
    )

    private fun getMeta() = Meta(
        requestTime = "2021-10-14T11:30:09.698Z",
        transactionId = "c0c9cf2c-be87-4872-af92-e4b73cd11863"
    )

    private fun getQualitySystemMessage() = SystemMessage(
        defaultMessage = "Quality System Message",
        effectiveFrom = 1632858692676,
        effectiveTo = 9932858689250,
        resourceId = "7008be71-26a9-4d91-90f3-3a3d0e47c578",
        translationKey = "bc23c228-e809-4950-8986-9f0cebef95f5",
        type = MESSAGE_TYPE_QUALITY
    )

    private fun getAlertSystemMessage() = SystemMessage(
        defaultMessage = "Alert System Message",
        effectiveFrom = 1632858692676,
        effectiveTo = 9932858689250,
        resourceId = ALERT_RES_ID,
        translationKey = "bc23c228-e809-4950-8986-9f0cebef95f5",
        type = MESSAGE_TYPE_ALERT
    )

    companion object {
        private const val BASE_URL = "baseUrl"
        private const val APP_SAMD_ID = "appOrSamdId"
        private const val APP_SAMD_VERSION = "appOrSamdVersion"
        private const val MESSAGE_TYPE_QUALITY = "quality"
        private const val MESSAGE_TYPE_ALERT = "alert"
        private const val ALERT_RES_ID = "alert res id"
    }
}