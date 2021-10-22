package com.roche.apprecall.test

import com.roche.apprecall.RecallException
import com.roche.apprecall.api.RecallApiClient
import com.roche.apprecall.mockresponses.SamdMockResponse
import com.roche.apprecall.runBlockingTest
import com.roche.apprecall.util.ApiMockEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.delay
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class SamdRecallApiClientTest {
    private val apiMockEngine = ApiMockEngine()
    private val apiMock = RecallApiClient(apiMockEngine.get())

    @Test
    fun countShouldMatch() = runBlockingTest {
        val response = apiMock.checkSaMDRecall(
            "https://floodlight.dhp-dev.dhs.platform.navify.com",
            "fr",
            listOf("com.roche.ssg.test.samd.one:1.0.0", "com.roche.ssg.test.samd.two:1.0.1")
        )
        assertEquals(2, response.size)
    }

    @Test
    fun shouldReturnCorrectSamd() = runBlockingTest {
        apiMockEngine.get().config
        val response = apiMock.checkSaMDRecall(
            "https://floodlight.dhp-dev.dhs.platform.navify.com/",
            "fr",
            listOf("com.roche.ssg.test.samd.one:1.0.0", "com.roche.ssg.test.samd.two:1.0.1")
        )
        assertEquals(response[0].samdId, "com.roche.ssg.test.samd.one")
        assertEquals(response[1].samdId, "com.roche.ssg.test.samd.two")
    }

    @Test
    fun shouldThrowExceptionIfSamdIsEmpty() = runBlockingTest {
        assertFailsWith<RecallException> {
            apiMock.checkSaMDRecall(
                "https://floodlight.dhp-dev.dhs.platform.navify.com",
                "fr",
                listOf()
            )
        }
    }

    @Test
    fun shouldThrowTimeoutException() = runBlockingTest {
        val mockEngine = MockEngine {
            delay(RecallApiClient.TIME_OUT + 5000)
            respond(SamdMockResponse(), HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))
        }
        val timeoutClient = RecallApiClient(mockEngine)
        assertFailsWith<RecallException> {
            timeoutClient.checkSaMDRecall(
                "https://floodlight.dhp-dev.dhs.platform.navify.com",
                "fr",
                listOf("com.roche.ssg.test.samd.one:1.0.0", "com.roche.ssg.test.samd.two:1.0.1")
            )
        }
    }
}