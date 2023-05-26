package com.example.listedapplication.di

import com.example.listedapplication.MyApplication.Companion.getBaseUrl
import com.example.listedapplication.MyApplication.Companion.getToken
import com.example.listedapplication.service.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(provideBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideOkHttpClient(interceptor: Interceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(24, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideTokenInterceptor() : Interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${provideToken()}")
            .build()
        chain.proceed(request)
    }

    @Provides
    fun provideBaseUrl(): String = getBaseUrl()

    @Provides
    fun provideToken(): String = getToken()
}