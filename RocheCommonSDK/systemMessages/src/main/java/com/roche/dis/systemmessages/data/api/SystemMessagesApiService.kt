package com.roche.dis.systemmessages.data.api

import com.roche.dis.systemmessages.data.model.SystemMessagesResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

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
        private const val BASE_URL = "https://defaultBaseUrl"
        internal const val SYSTEM_MESSAGES_END_POINT = "/notifications/system-messages"

        private var retrofitService: SystemMessagesApiService? = null

        fun getInstance(): SystemMessagesApiService {
            if (retrofitService == null) {
                val retrofit = RetrofitApiService.getRetrofit(BASE_URL, TIMEOUT)
                retrofitService = retrofit.create(SystemMessagesApiService::class.java)
            }
            return retrofitService!!
        }
    }
}