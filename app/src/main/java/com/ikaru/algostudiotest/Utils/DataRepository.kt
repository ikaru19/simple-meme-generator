package com.ikaru.algostudiotest.Utils

import android.text.TextUtils
import com.ikaru.algostudiotest.Services.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object DataRepository {

    private const val BASE_URL = "https://api.imgflip.com"

    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())



    private val logging = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClient = OkHttpClient.Builder()


    public fun create(): ApiService {
        val retrofit = builder
            .build()
        return retrofit.create(ApiService::class.java)
    }


}