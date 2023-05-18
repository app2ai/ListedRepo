package com.example.listedapplication.service

import com.example.listedapplication.utils.Constants.ACCESS_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $ACCESS_TOKEN")
            .build()
        return chain.proceed(request)
    }
}