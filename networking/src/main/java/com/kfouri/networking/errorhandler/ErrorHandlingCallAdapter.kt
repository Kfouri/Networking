package com.kfouri.networking.errorhandler

import com.kfouri.networking.call.CustomCall
import com.kfouri.networking.call.CustomCallAdapter
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type
import java.util.concurrent.Executor

class ErrorHandlingCallAdapter<T>(
    responseType: Type?,
    callbackExecutor: Executor?
) : CallAdapter<T, CustomCall<T>> {

    private var responseType: Type? = null
    private var callbackExecutor: Executor? = null

    init {
        this.responseType = responseType
        this.callbackExecutor = callbackExecutor
    }

    override fun responseType(): Type? {
        return responseType
    }

    override fun adapt(call: Call<T?>): CustomCall<T>? {
        return CustomCallAdapter(call, callbackExecutor) as CustomCall<T>?

    }

}