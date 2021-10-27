package com.roche.ssg.pushnotification.util

import com.roche.ssg.pushnotification.api.PushNotificationApiClient
import com.roche.ssg.pushnotification.mockresponse.DeregisterMockResponse
import com.roche.ssg.pushnotification.mockresponse.RegisterMockResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.features.HttpTimeout
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf

class ApiMockEngine {
    fun get() = client.engine

    private val responseHeaders =
        headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
    private val client = HttpClient(MockEngine) {
        install(HttpTimeout) {
            requestTimeoutMillis = PushNotificationApiClient.TIME_OUT
            connectTimeoutMillis = PushNotificationApiClient.TIME_OUT
        }
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    PushNotificationApiClient.REGISTER_END_POINT -> {

                        when {
                            request.headers["Authorization"]?.contains("invalid_mock_auth_token") == true -> {
                                respond(
                                    "{\"message\":\"Unauthorized\"}",
                                    HttpStatusCode.Unauthorized,
                                    headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                                )
                            }
                            else -> {
                                respond(RegisterMockResponse(), HttpStatusCode.OK, responseHeaders)
                            }
                        }

                    }
                    PushNotificationApiClient.DEREGISTER_END_POINT -> {

                        when {
                            request.headers["Authorization"]?.contains("invalid_mock_auth_token") == true -> {
                                respond(
                                    "{\"message\":\"Unauthorized\"}",
                                    HttpStatusCode.Unauthorized,
                                    headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                                )
                            }
                            else -> {
                                respond(
                                    DeregisterMockResponse(),
                                    HttpStatusCode.OK,
                                    responseHeaders
                                )
                            }
                        }

                    }
                    else -> {
                        error("Unhandled ${request.url.encodedPath}")
                    }

                }
            }
        }
    }
}