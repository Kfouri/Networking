package com.kfouri.networking.util.deserializer

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonUtil {

    private fun createDefaultGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    private var currentGson: Gson = createDefaultGson()

    fun setCustomGson(customGson: Gson) {
        currentGson = customGson
    }

    fun getGson(): Gson {
        return currentGson
    }
}