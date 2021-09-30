package com.roche.apprecall.util

import com.roche.apprecall.data.RecallApiClient
import com.roche.apprecall.mockresponses.AppRecallResponse
import com.roche.apprecall.mockresponses.SamdMockResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.features.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf

class ApiMockEngine {

    fun get() = client.engine

    private val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
    private val client = HttpClient(MockEngine) {
        install(HttpTimeout) {
            requestTimeoutMillis = RecallApiClient.TIME_OUT
            connectTimeoutMillis = RecallApiClient.TIME_OUT
        }
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    RecallApiClient.SAMD_RECALL_END_POINT -> {
                        val samds = request.url.parameters["samds"]
                        if (samds.isNullOrBlank()) {
                            respond(
                                "Empty query params not supported.", HttpStatusCode.BadRequest, responseHeaders
                            )
                        } else {
                            respond(SamdMockResponse(), HttpStatusCode.OK, responseHeaders)
                        }
                    }
                    RecallApiClient.APP_RECALL_END_POINT -> {
                        val appId = request.url.parameters["appId"]
                        val appVersion = request.url.parameters["appVersion"]
                        val country = request.url.parameters["country"]
                        if (appId.isNullOrBlank() ||
                            appVersion.isNullOrBlank() ||
                            country.isNullOrBlank()
                        ) {
                            respond(
                                "Empty query params not supported.", HttpStatusCode.BadRequest, responseHeaders
                            )
                        } else {
                            respond(AppRecallResponse(), HttpStatusCode.OK, responseHeaders)
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