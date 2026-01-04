package com.kfouri.networking

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NetworkingApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initHttpClient()
    }

    private fun initHttpClient() {
        HttpClient.init(
            baseUrl = "https://jsonplaceholder.typicode.com/",
            context = this,
            isDebug = true,
            deviceID = "123456789"
        )
    }
}