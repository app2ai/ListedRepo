package com.example.listedapplication

import android.app.Application
import com.example.listedapplication.di.DaggerMyApplicationComponent
import com.example.listedapplication.di.MyApplicationComponent

class MyApplication : Application() {
    lateinit var appComponent: MyApplicationComponent

    override fun onCreate() {
        super.onCreate()
        // Initialise app component here
        appComponent = DaggerMyApplicationComponent.factory().create(applicationContext)
    }
}