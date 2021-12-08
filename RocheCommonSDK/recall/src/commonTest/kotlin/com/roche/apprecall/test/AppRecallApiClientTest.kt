package com.roche.apprecall.test

import com.roche.apprecall.RecallException
import com.roche.apprecall.api.RecallApiClient
import com.roche.apprecall.mockresponses.AppRecallResponse
import com.roche.apprecall.runBlockingTest
import com.roche.apprecall.util.ApiMockEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.delay
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class AppRecallApiClientTest {

    private val apiMockEngine = ApiMockEngine()
    private val apiMock = RecallApiClient(apiMockEngine.get())

    @Test
    fun shouldGetValidResponse() = runBlockingTest {
        val response = apiMock.checkAppRecall(
            "https://floodlight.dhp-dev.dhs.platform.navify.com",
            "com.roche.ssg.test.application",
            "1.0",
            "fr"
        )
        assertNotNull(response)
    }

    @Test
    fun shouldThrowExceptionAppIdIsEmpty() = runBlockingTest {
        assertFailsWith<RecallException> {
            apiMock.checkAppRecall(
                "https://floodlight.dhp-dev.dhs.platform.navify.com",
                "",
                "1.0",
                "fr"
            )
        }
    }

    @Test
    fun shouldThrowTimeoutException() = runBlockingTest {
        val mockEngine = MockEngine {
            delay(RecallApiClient.TIME_OUT + 5000)
            respond(AppRecallResponse(), HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))
        }
        val timeoutClient = RecallApiClient(mockEngine)
        assertFailsWith<RecallException> {
            timeoutClient.checkAppRecall(
                "https://floodlight.dhp-dev.dhs.platform.navify.com",
                "com.roche.ssg.test.application",
                "1.0",
                "fr"
            )
        }
    }
}