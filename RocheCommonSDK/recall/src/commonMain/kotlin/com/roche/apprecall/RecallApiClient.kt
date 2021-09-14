package com.roche.apprecall

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.features.ResponseException
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.core.use
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class RecallApiClient {

    private val TIME_OUT: Long = 60_000
    private val APP_RECALL_END_POINT = "/recall/application"
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
            httpClient.get(baseURL.plus(APP_RECALL_END_POINT)) {
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

    suspend fun getSaMDRecall(baseURL: String, country: String, samdIds: List<String>): Result<List<SamdResponse>> {
        try {
            httpClient.use {
                val rawSamd: String =
                    httpClient.get(baseURL.plus(SaMD_RECALL_END_POINT)) {
                        parameter("os", getOS())
                        parameter("osVersion", getOSVersion())
                        parameter("device", getDevice())
                        parameter("samds", samdIds.joinToString(","))
                        parameter("country", country)
                        contentType(ContentType.Application.Json)
                    }
                /*val rawSamd = "{\n" +
                        "\t\"com.roche.pinchtomatoes\": {\n" +
                        "\t\t\"recall\": true\n" +
                        "\t},\n" +
                        "\t\"com.roche.walktest\": {\n" +
                        "\t\t\"recall\": false\n" +
                        "\t}\n" +
                        "}"*/
                val format = Json
                val jsonObject = format.parseToJsonElement(rawSamd)
                val responseList = mutableListOf<SamdResponse>()
                for (samdId in samdIds) {
                    val recallStatus =
                        jsonObject.jsonObject[samdId]?.jsonObject?.get("recall")?.jsonPrimitive?.boolean!!
                    responseList.add(SamdResponse(samdId, recallStatus))
                }
                return Result.success(responseList)
            }
        } catch (ex: ResponseException) {
            return Result.failure(ex)
        }
    }
}