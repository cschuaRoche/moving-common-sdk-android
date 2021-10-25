package com.roche.ssg.systemmessages.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface RetrofitApiService {

    /**
     * These are the errors we can get from the server
     */
    object APIResponseCode {
        const val SUCCESS_200 = 200
        const val SUCCESS_201 = 201
        const val INPUT_VALIDATION_ERROR = 400
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val CONFLICT = 409
        const val MISSING_REQUIRED_PARAMS = 400
        const val INTERNAL_SERVER_ERROR = 500
        const val TIMEOUT = 504
    }

    /**
     * Exception from API with [statusCode] given by server
     */
    data class ApiException(val statusCode: Int) : Exception()

    companion object {
        fun getRetrofit(baseUrl: String, timeout: Long): Retrofit {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder().apply {
                connectTimeout(timeout, TimeUnit.SECONDS)
                readTimeout(timeout, TimeUnit.SECONDS)
                writeTimeout(timeout, TimeUnit.SECONDS)
                addInterceptor(httpLoggingInterceptor)
            }.build()

            return Retrofit.Builder().apply {
                baseUrl(baseUrl)
                client(okHttpClient)
                addConverterFactory(GsonConverterFactory.create())
            }.build()
        }
    }
}