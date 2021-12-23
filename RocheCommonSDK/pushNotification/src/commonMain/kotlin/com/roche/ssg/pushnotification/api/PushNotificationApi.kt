package com.roche.ssg.pushnotification.api

import com.roche.ssg.pushnotification.PushNotificationException
import com.roche.ssg.pushnotification.getDevice
import com.roche.ssg.pushnotification.getMake
import com.roche.ssg.pushnotification.getOS
import com.roche.ssg.pushnotification.getOSVersion
import com.roche.ssg.pushnotification.model.DeregisterRequest
import com.roche.ssg.pushnotification.model.DeregisterResponse
import com.roche.ssg.pushnotification.model.DeviceInfo
import com.roche.ssg.pushnotification.model.Metadata
import com.roche.ssg.pushnotification.model.RegisterRequest
import com.roche.ssg.pushnotification.model.RegisterResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.HttpRequestTimeoutException
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.ResponseException
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.network.sockets.ConnectTimeoutException
import io.ktor.network.sockets.SocketTimeoutException
import io.ktor.utils.io.core.use


class PushNotificationApi(httpClientEngine: HttpClientEngine) {

    constructor() : this(HttpClient().engine)

    private val httpClient: HttpClient = HttpClient(httpClientEngine) {
        install(HttpTimeout) {
            requestTimeoutMillis = TIME_OUT
            connectTimeoutMillis = TIME_OUT
        }

        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            }
            serializer = KotlinxSerializer(json)
        }
    }

    /**
     * @param baseURL Host URL
     * @param appId Application ID
     * @param userId User ID
     * @param firebaseToken Firebase token, which will be registered in Backend
     * @param authorizationToken It need for authentication
     */
    @Throws(PushNotificationException::class,Exception::class)
    suspend fun registerDevice(
        baseURL: String,
        appId: String,
        userId: String,
        channelType: String,
        firebaseToken: String,
        authorizationToken: String,
        appVersion: String = "",
        country: String = "",
        orgId: String = "",
        hcpId: String = ""
    ): RegisterResponse {
        try {
            return httpClient.use {
                val response: RegisterResponse =
                    httpClient.post(getCallingUrl(baseURL, REGISTER_END_POINT)) {

                        body = RegisterRequest(
                            userId, firebaseToken, getOS(), channelType,DeviceInfo(
                                getOSVersion(), getDevice(),
                                getMake(), appVersion
                            ), Metadata(country, orgId, hcpId)
                        )

                        contentType(ContentType.Application.Json)
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $authorizationToken")
                            append("dhp-app-id", appId)
                        }
                    }
                return@use response
            }
        } catch (ex: ResponseException) {
            val exception = PushNotificationException(ex.response.status.value, ex)
            throw exception
        } catch (ex: Exception) {
            when (ex) {
                is HttpRequestTimeoutException, is SocketTimeoutException, is ConnectTimeoutException -> {
                    val recallException =
                        PushNotificationException(HttpStatusCode.RequestTimeout.value, ex)
                    throw recallException
                }
                else ->
                    throw ex
            }
        }
    }

    /**
     * @param baseURL Host URL
     * @param appId Application ID
     * @param userId User ID
     * @param firebaseToken Firebase token, which will be registered in Backend
     * @param authorizationToken It need for authentication
     */
    @Throws(PushNotificationException::class,Exception::class)
    suspend fun deregisterDevice(
        baseURL: String,
        appId: String,
        userId: String,
        authorizationToken: String,
        firebaseToken: String = "",
    ): DeregisterResponse {
        try {
            return httpClient.use {
                val response: DeregisterResponse =
                    httpClient.post(getCallingUrl(baseURL, DEREGISTER_END_POINT)) {

                        body = DeregisterRequest(userId, firebaseToken)

                        contentType(ContentType.Application.Json)
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $authorizationToken")
                            append("dhp-app-id", appId)
                        }
                    }
                return@use response
            }
        } catch (ex: ResponseException) {
            val exception = PushNotificationException(ex.response.status.value, ex)
            throw exception
        } catch (ex: Exception) {
            when (ex) {
                is HttpRequestTimeoutException, is SocketTimeoutException, is ConnectTimeoutException -> {
                    val recallException =
                        PushNotificationException(HttpStatusCode.RequestTimeout.value, ex)
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
        const val REGISTER_END_POINT = "/notifications/push/register-device"
        const val DEREGISTER_END_POINT = "/notifications/push/deregister-device"
    }

}