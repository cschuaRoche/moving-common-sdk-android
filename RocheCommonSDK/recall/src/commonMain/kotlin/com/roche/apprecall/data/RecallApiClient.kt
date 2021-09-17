package com.roche.apprecall.data

import com.roche.apprecall.AppRecallResponse
import com.roche.apprecall.RecallException
import com.roche.apprecall.SamdResponse
import com.roche.apprecall.getDevice
import com.roche.apprecall.getOS
import com.roche.apprecall.getOSVersion
import com.roche.apprecall.initLogger
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
            val json = Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            }
            serializer = KotlinxSerializer(json)
        }.also {
            initLogger()
        }
    }

    suspend fun checkAppRecall(baseURL: String, appId: String, appVersion: String, country: String): AppRecallResponse {
        try {
            return httpClient.use {
                val response: AppRecallResponse = httpClient.get(getCallingUrl(baseURL, APP_RECALL_END_POINT)) {
                    parameter("os", getOS())
                    parameter("osVersion", getOSVersion())
                    parameter("device", getDevice())
                    parameter("appId", appId)
                    parameter("appVersion", appVersion)
                    parameter("country", country)
                    contentType(ContentType.Application.Json)
                }
                return@use response
            }
        } catch (ex: ResponseException) {
            val exception = RecallException(ex.response.status.value, ex)
            throw exception
        } catch (ex: Exception) {
            throw ex
        }
    }

    suspend fun checkSaMDRecall(baseURL: String, country: String, samdIds: List<String>): List<SamdResponse> {
        try {
            return httpClient.use {
                val rawSamd: String =
                    httpClient.get(getCallingUrl(baseURL, SaMD_RECALL_END_POINT)) {
                        parameter("os", getOS())
                        parameter("osVersion", getOSVersion())
                        parameter("device", getDevice())
                        parameter("samds", samdIds.joinToString(","))
                        parameter("country", country)
                        contentType(ContentType.Application.Json)
                    }

                val format = Json
                val jsonObject = format.parseToJsonElement(rawSamd).jsonObject
                val responseList = mutableListOf<SamdResponse>()
                for (samdId in jsonObject.keys) {
                    val recallStatus =
                        jsonObject.jsonObject[samdId]?.jsonObject?.get("recall")?.jsonPrimitive?.boolean!!
                    responseList.add(SamdResponse(samdId, recallStatus))
                }
                return@use responseList
            }
        } catch (ex: ResponseException) {
            val exception =
                RecallException(
                    ex.response.status.value,
                    ex
                )
            throw exception
        } catch (ex: Exception) {
            throw ex
        }
    }

    private fun getCallingUrl(baseUrl: String, endpoint: String): String {
        return if (baseUrl.endsWith("/")) baseUrl.plus(endpoint) else baseUrl.plus("/").plus(endpoint)
    }

    companion object {
        private const val TIME_OUT: Long = 15_000
        private const val APP_RECALL_END_POINT = "recall/application"
        private const val SaMD_RECALL_END_POINT = "recall/samd"
    }
}