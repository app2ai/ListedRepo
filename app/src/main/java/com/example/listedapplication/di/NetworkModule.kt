package com.example.listedapplication.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.listedapplication.service.ApiService
import com.example.listedapplication.utils.Constants
import com.example.listedapplication.utils.Constants.TOKEN_KEY
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
            .baseUrl(Constants.BASE_URL)
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
    fun provideTokenInterceptor(sharedPreferences: SharedPreferences) : Interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${sharedPreferences.getString(TOKEN_KEY, "")}")
            .build()
        chain.proceed(request)
    }

    @Provides
    fun provideEncryptedSharedPreferences(context: Context) : SharedPreferences {
        val e = EncryptedSharedPreferences.create(
            "listed_pref",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        e.edit().putString(TOKEN_KEY, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI").apply()
        return e
    }
}