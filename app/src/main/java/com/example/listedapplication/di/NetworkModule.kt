package com.example.listedapplication.di

import com.example.listedapplication.service.ApiService
import com.example.listedapplication.service.RetrofitClient
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    @Provides
    fun provideCarApiService(): ApiService {
        return RetrofitClient.service
    }
}