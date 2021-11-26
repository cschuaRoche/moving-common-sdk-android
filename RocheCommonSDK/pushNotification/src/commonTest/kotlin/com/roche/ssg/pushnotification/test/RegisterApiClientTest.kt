package com.roche.ssg.pushnotification.test

import com.roche.ssg.pushnotification.PushNotificationException
import com.roche.ssg.pushnotification.api.PushNotificationApi
import com.roche.ssg.pushnotification.runBlockingTest
import com.roche.ssg.pushnotification.util.ApiMockEngine
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class RegisterApiClientTest {

    private val apiMockEngine = ApiMockEngine()
    private val apiMock = PushNotificationApi(apiMockEngine.get())

    @Test
    fun shouldGetValidResponse() = runBlockingTest {
        val response = apiMock.registerDevice(
            "https://floodlight.dhp-dev.dhs.platform.navify.com",
            "test",
            "1234",
            "mock_firebase_token",
            "1.3.1",
            "valid_mock_auth_token",
            "us",
            "mock_os",
            "mock_os_version",
            "mock_device",
            "mock_make"
        )
        assertNotNull(response)
    }

    @Test
    fun shouldThrowExceptionIfAuthTokenIsWrong() = runBlockingTest {
        assertFailsWith<PushNotificationException> {
            apiMock.registerDevice(
                "https://floodlight.dhp-dev.dhs.platform.navify.com",
                "test",
                "1234",
                "mock_firebase_token",
                "1.3.1",
                "invalid_mock_auth_token",
                "us",
                "mock_os",
                "mock_os_version",
                "mock_device",
                "mock_make"
            )
        }
    }
}