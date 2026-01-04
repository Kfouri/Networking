package com.kfouri.networking.responsehandler.callback

import com.kfouri.networking.responsehandler.RetrofitCallbackWrapper
import retrofit2.Response
import java.io.IOException
import kotlin.jvm.Throws

interface RetrofitCallWrapper<T> {
    fun cancel()
    fun enqueue(callback: RetrofitCallbackWrapper<T>)
    @Throws(IOException::class)
    fun execute(): Response<T>
}