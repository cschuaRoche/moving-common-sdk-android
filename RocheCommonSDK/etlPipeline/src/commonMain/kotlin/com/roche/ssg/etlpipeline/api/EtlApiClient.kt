package com.roche.ssg.etlpipeline.api

import com.roche.ssg.etlpipeline.EtlException
import com.roche.ssg.etlpipeline.model.SignedUrlResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.HttpRequestTimeoutException
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.ResponseException
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.network.sockets.ConnectTimeoutException
import io.ktor.network.sockets.SocketTimeoutException
import io.ktor.utils.io.core.use


class EtlApiClient(httpClientEngine: HttpClientEngine) {

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
     * @param baseUrl Host URL
     * @param authorizationToken It need for authentication
     * @param dhpAssessmentId
     * @param dhpAppId Application ID
     * @param xAmzTagging All Tags
     *
     */
    @Throws(EtlException::class, Exception::class)
    suspend fun getPreSignedUrl(
        baseUrl: String,
        authorizationToken: String,
        dhpAssessmentId: String,
        dhpAppId: String,
        xAmzTagging: HashMap<String, String>
    ): SignedUrlResponse {
        try {
            return httpClient.use {
                val response: SignedUrlResponse =
                    httpClient.get(getCallingUrl(baseUrl, SIGNED_URL_END_POINT)) {

                        contentType(ContentType.Application.Json)
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $authorizationToken")
                            append("dhp-assessment-id", dhpAssessmentId)
                            append("dhp-app-id", dhpAppId)
                            append("x-amz-tagging", xAmzTagging.map { it.key +"="+it.value }.joinToString("&"))
                        }
                    }
                return@use response
            }
        } catch (ex: ResponseException) {
            val exception = EtlException(ex.response.status.value, ex)
            throw exception
        } catch (ex: Exception) {
            when (ex) {
                is HttpRequestTimeoutException, is SocketTimeoutException, is ConnectTimeoutException -> {
                    val recallException =
                        EtlException(HttpStatusCode.RequestTimeout.value, ex)
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
        const val SIGNED_URL_END_POINT = "/signed-url"
    }
}