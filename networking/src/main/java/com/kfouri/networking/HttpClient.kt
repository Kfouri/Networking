package com.kfouri.networking

import android.app.Application
import com.kfouri.networking.errorhandler.ErrorHandlingCallAdapterFactory
import com.kfouri.networking.responsehandler.interceptedresponse.InterceptedResponseAdapterFactory
import com.kfouri.networking.responsehandler.callback.RetrofitCallWrapperAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

object HttpClient {
    private const val CONNECTION_TIME_OUT = 35L
    private const val READ_TIME_OUT = 35L

    private var httpClient: Retrofit? = null
    private var mBaseUrl: String? = null
    private var mContext: Application? = null
    private var mIsDebug: Boolean? = null
    private var mDeviceID: String? = null

    private val extraInterceptor: MutableList<Interceptor> = mutableListOf()
    private var okHttpClientCustomizer: ((OkHttpClient.Builder) -> Unit)? = null

    @JvmStatic
    fun init(
        baseUrl: String,
        context: Application,
        isDebug: Boolean,
        deviceID: String
    ) {
        mBaseUrl = baseUrl
        mContext = context
        mIsDebug = isDebug
        mDeviceID = deviceID
    }

    @Throws
    @JvmStatic
    fun getClient(): Retrofit {
        if (httpClient == null) buildClient()
        return httpClient ?: run { throw Exception("HttpClient not initialized") }
    }

    fun createClient(
        baseUrl: String = mBaseUrl.orEmpty()
    ): Retrofit {
        if (mContext == null || baseUrl.isEmpty()) {
            throw Exception("HttpClient not initialized")
        }
        val okHttpClientBuilder = OkHttpClient.Builder().apply {
            if (extraInterceptor.isNotEmpty()) {
                extraInterceptor.forEach { addInterceptor(it) }
            }

            readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
        }
        okHttpClientCustomizer?.invoke(okHttpClientBuilder)
        val okHttpClient = okHttpClientBuilder.build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .addCallAdapterFactory(InterceptedResponseAdapterFactory())
            .addCallAdapterFactory(ErrorHandlingCallAdapterFactory())
            .addCallAdapterFactory(RetrofitCallWrapperAdapterFactory())
            .build()
    }

    private fun buildClient() {
        httpClient = createClient()
    }
}