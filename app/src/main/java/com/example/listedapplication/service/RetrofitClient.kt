package com.example.listedapplication.service

import com.example.listedapplication.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// Retrofit client object
@Singleton
object RetrofitClient {
    val service: ApiService
        get(){
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

    private val okHttpClient: OkHttpClient
        get(){
            return OkHttpClient.Builder()
                .connectTimeout(24, TimeUnit.SECONDS)
                .build()
        }
}