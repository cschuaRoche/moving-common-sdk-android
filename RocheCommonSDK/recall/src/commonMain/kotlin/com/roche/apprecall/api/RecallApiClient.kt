package com.roche.apprecall.api

import com.roche.apprecall.model.AppRecallResponse
import com.roche.apprecall.RecallException
import com.roche.apprecall.model.SamdResponse
import com.roche.apprecall.getDevice
import com.roche.apprecall.getOS
import com.roche.apprecall.getOSVersion
import com.roche.apprecall.initLogger
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.HttpRequestTimeoutException
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.ResponseException
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.network.sockets.ConnectTimeoutException
import io.ktor.network.sockets.SocketTimeoutException
import io.ktor.utils.io.core.use
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class RecallApiClient(httpClientEngine: HttpClientEngine) {

    constructor() : this(HttpClient().engine)

    private val httpClient: HttpClient = HttpClient(httpClientEngine) {
        install(HttpTimeout) {
            requestTimeoutMillis = TIME_OUT
            connectTimeoutMillis = TIME_OUT
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    //Napier.v(tag = "HTTP Client", message = message)
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
    @Throws(RecallException::class,Exception::class)
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
            when (ex) {
                is HttpRequestTimeoutException, is SocketTimeoutException, is ConnectTimeoutException -> {
                    val recallException = RecallException(HttpStatusCode.RequestTimeout.value, ex)
                    throw recallException
                }
                else ->
                    throw ex
            }
        }
    }
    @Throws(RecallException::class,Exception::class)
    suspend fun checkSaMDRecall(baseURL: String, country: String, samdIds: List<String>): List<SamdResponse> {
        try {
            return httpClient.use {
                val rawSamd: String = httpClient.get(getCallingUrl(baseURL, SAMD_RECALL_END_POINT)) {
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
            when (ex) {
                is HttpRequestTimeoutException, is SocketTimeoutException, is ConnectTimeoutException -> {
                    val recallException = RecallException(HttpStatusCode.RequestTimeout.value, ex)
                    throw recallException
                }
                else ->
                    throw ex
            }
        }
    }

    private fun getCallingUrl(baseUrl: String, endpoint: String): String {
        val url = if (baseUrl.endsWith("/")) {
            baseUrl.replace(Regex("/$"), "").plus(endpoint)
        } else
            baseUrl.plus(endpoint)
        return url
    }

    companion object {
        const val TIME_OUT: Long = 15_000
        const val APP_RECALL_END_POINT = "/recall/application"
        const val SAMD_RECALL_END_POINT = "/recall/samd"
    }
}