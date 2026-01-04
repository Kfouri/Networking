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

    /*
    private fun setupCustomGsonIfNecessary() {
        // 1. Crea tu instancia de Gson con la configuración que necesites.
        val customGson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, MyDateDeserializer()) // Tu lógica personalizada
            .serializeNulls() // Otra configuración específica
            .create()

        // 2. Proporciona esta instancia a la librería de networking.
        GsonUtil.setCustomGson(customGson)
    }
     */
}