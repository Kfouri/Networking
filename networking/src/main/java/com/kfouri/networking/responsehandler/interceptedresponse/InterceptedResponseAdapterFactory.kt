package com.kfouri.networking.responsehandler.interceptedresponse

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class InterceptedResponseAdapterFactory: CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation?>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        check(returnType is ParameterizedType) {
            "Return type must be Call<InterceptedResponse<T>> or Call<InterceptedResponse<out T>>."
        }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != InterceptedResponse::class.java) {
            return null
        }

        check(responseType is ParameterizedType) {
            "Response type must be a parameterized as InterceptedResponse<T> or InterceptedResponse<out T>."
        }

        val successBodyType = getParameterUpperBound(0, responseType)
        return InterceptedResponseAdapter<Any>(successBodyType)
    }

}