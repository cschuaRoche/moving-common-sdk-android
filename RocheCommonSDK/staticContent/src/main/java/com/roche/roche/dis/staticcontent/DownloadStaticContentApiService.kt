package com.roche.roche.dis.staticcontent

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface DownloadStaticContentApiService {
    @GET
    fun fetchStaticContentInfo(@Url url: String): Call<ResponseBody>

    companion object {
        var retrofitService: DownloadStaticContentApiService? = null

        fun getInstance(): DownloadStaticContentApiService {
            if (retrofitService == null) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://defaultBaseUrl")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(DownloadStaticContentApiService::class.java)
            }
            return retrofitService!!
        }
    }
}