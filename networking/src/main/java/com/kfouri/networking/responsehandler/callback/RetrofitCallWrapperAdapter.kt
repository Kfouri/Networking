package com.kfouri.networking.responsehandler.callback

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class RetrofitCallWrapperAdapter<T>(private val responseType: Type) :
    CallAdapter<T, RetrofitCallWrapper<T>> {
    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<T>): RetrofitCallWrapper<T> {
        return RetrofitCallWrapperImpl(call)

    }
}