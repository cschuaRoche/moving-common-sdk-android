package com.roche.ssg.etlpipeline.util

import com.roche.ssg.etlpipeline.api.EtlRepository
import com.roche.ssg.etlpipeline.mockresponse.SignedUrlMockResponse
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
            requestTimeoutMillis = EtlRepository.TIME_OUT
            connectTimeoutMillis = EtlRepository.TIME_OUT
        }
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    EtlRepository.SIGNED_URL_END_POINT -> {

                        when {
                            request.headers["Authorization"]?.contains("invalid_mock_auth_token") == true -> {
                                respond(
                                    "{\"message\":\"Unauthorized\"}",
                                    HttpStatusCode.Unauthorized,
                                    headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                                )
                            }
                            request.headers["dhp-assessment-id"]?.trim()?.isEmpty() == true -> {
                                respond(
                                    "{\"message\": \"Missing header value: dhp-assessment-id\"}",
                                    HttpStatusCode.BadRequest,
                                    headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                                )
                            }
                            request.headers["dhp-app-id"]?.trim()?.isEmpty() == true -> {
                                respond(
                                    "{\"message\": \"Missing header value: dhp-app-id\"}",
                                    HttpStatusCode.BadRequest,
                                    headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                                )
                            }
                            else -> {
                                respond(SignedUrlMockResponse(), HttpStatusCode.OK, responseHeaders)
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