package com.kfouri.networking.responsehandler.callback

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class RetrofitCallWrapperAdapterFactory: CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation?>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        if (getRawType(returnType) != RetrofitCallWrapper::class.java) {
            return null
        }

        check(returnType is ParameterizedType) { "RetrofitCallWrapper must be parameterized as RetrofitCallWrapper<Foo> or RetrofitCallWrapper<? extends Foo>" }
        val responseType = getParameterUpperBound(0, returnType)
        return RetrofitCallWrapperAdapter<Any>(responseType)
    }
}