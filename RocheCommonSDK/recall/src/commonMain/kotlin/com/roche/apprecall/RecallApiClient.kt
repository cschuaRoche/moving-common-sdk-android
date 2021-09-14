package com.roche.apprecall

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.core.*

object RecallApiClient {

    private val TIME_OUT: Long = 60_000
    private val APP_RECAL_END_POINT = "/recall/application"
    private val SaMD_RECALL_END_POINT = "/recall/samd"

    private val httpClient: HttpClient = HttpClient {
        install(HttpTimeout) {
            requestTimeoutMillis = TIME_OUT
            connectTimeoutMillis = TIME_OUT
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.v(tag = "HTTP Client", message = message)
                }
            }
        }
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            }
            serializer = KotlinxSerializer(json)
        }.also {
            initLogger()
        }
    }

    suspend fun getAppRecall(baseURL: String, appId: String, appVersion: String, country: String) {
        return httpClient.use {
            httpClient.get(baseURL.plus(APP_RECAL_END_POINT)) {
                parameter("os", getOS())
                parameter("osVersion", getOSVersion())
                parameter("device", getDevice())
                parameter("appId",appId)
                parameter("appVersion",appVersion)
                parameter("country",country)
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun getSaMDRecall(baseURL: String, samds: String, country: String) {
        return httpClient.use {
            httpClient.get(baseURL.plus(SaMD_RECALL_END_POINT)) {
                parameter("os", getOS())
                parameter("osVersion", getOSVersion())
                parameter("device", getDevice())
                parameter("samds",samds)
                parameter("country",country)
                contentType(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
            }
        }
    }
}