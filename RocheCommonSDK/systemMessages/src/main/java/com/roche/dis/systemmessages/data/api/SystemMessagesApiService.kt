package com.roche.dis.systemmessages.data.api

import com.roche.dis.systemmessages.data.model.SystemMessagesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

internal interface SystemMessagesApiService {
    @GET
    suspend fun getSystemMessages(
        @Url url: String,
        @Query("device") device: String,
        @Query("os") os: String,
        @Query("osVersion") osVersion: String,
        @Query("appOrSamdId") appOrSamdId: String,
        @Query("appOrSamdVersion") appOrSamdVersion: String,
        @Query("country") country: String? = null
    ): SystemMessagesResponse

    companion object {
        private const val TIMEOUT = 15L
        internal const val SYSTEM_MESSAGES_END_POINT = "/notifications/systemMessages"

        var retrofitService: SystemMessagesApiService? = null

        fun getInstance(): SystemMessagesApiService {
            if (retrofitService == null) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                val okHttpClient = OkHttpClient.Builder().apply {
                    connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                    addInterceptor(httpLoggingInterceptor)
                }.build()

                val retrofit = Retrofit.Builder().apply {
                    baseUrl("https://defaultBaseUrl")
                    client(okHttpClient)
                    addConverterFactory(GsonConverterFactory.create())
                }.build()

                retrofitService = retrofit.create(SystemMessagesApiService::class.java)
            }
            return retrofitService!!
        }
    }
}