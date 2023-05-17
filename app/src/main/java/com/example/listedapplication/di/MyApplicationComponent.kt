package com.example.listedapplication.di

import android.content.Context
import com.example.listedapplication.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

// App component dependency provider
@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface MyApplicationComponent {
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): MyApplicationComponent
    }
}