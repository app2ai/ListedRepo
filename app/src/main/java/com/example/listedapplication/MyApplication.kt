package com.example.listedapplication

import android.app.Application
import com.example.listedapplication.di.DaggerMyApplicationComponent
import com.example.listedapplication.di.MyApplicationComponent

class MyApplication : Application() {
    lateinit var appComponent: MyApplicationComponent

    companion object {
        external fun getBaseUrl(): String
        external fun getToken(): String
    }

    override fun onCreate() {
        super.onCreate()
        // Initialise app component here
        appComponent = DaggerMyApplicationComponent.factory().create(applicationContext)

        // Used to load the 'listedapplication' library on application startup.
        System.loadLibrary("listedapplication")
    }
}