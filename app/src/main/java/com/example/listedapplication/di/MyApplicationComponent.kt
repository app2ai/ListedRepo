package com.example.listedapplication.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.listedapplication.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import javax.inject.Singleton

// App component dependency provider
@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class, SharePrefModule::class])
interface MyApplicationComponent {
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): MyApplicationComponent
    }
}