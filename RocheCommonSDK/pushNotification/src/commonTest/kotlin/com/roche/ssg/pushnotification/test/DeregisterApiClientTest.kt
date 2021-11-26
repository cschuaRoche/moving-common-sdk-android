package com.roche.ssg.pushnotification.test

import com.roche.ssg.pushnotification.PushNotificationException
import com.roche.ssg.pushnotification.api.PushNotificationApi
import com.roche.ssg.pushnotification.runBlockingTest
import com.roche.ssg.pushnotification.util.ApiMockEngine
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class DeregisterApiClientTest {
    private val apiMockEngine = ApiMockEngine()
    private val apiMock = PushNotificationApi(apiMockEngine.get())

    @Test
    fun shouldGetValidResponse() = runBlockingTest {
        val response = apiMock.deregisterDevice(
            "https://floodlight.dhp-dev.dhs.platform.navify.com",
            "test",
            "1234",
            "valid_mock_auth_token",
            "mock_firebase_token"
        )
        assertNotNull(response)
    }

    @Test
    fun shouldThrowExceptionIfAuthTokenIsWrong() = runBlockingTest {
        assertFailsWith<PushNotificationException> {
            val response = apiMock.deregisterDevice(
                "https://floodlight.dhp-dev.dhs.platform.navify.com",
                "test",
                "1234",
                "invalid_mock_auth_token",
                "mock_firebase_token"
            )
        }
    }
}