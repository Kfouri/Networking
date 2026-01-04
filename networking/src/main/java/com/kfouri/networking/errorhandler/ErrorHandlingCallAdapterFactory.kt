package com.kfouri.networking.errorhandler

import com.kfouri.networking.call.CustomCall
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ErrorHandlingCallAdapterFactory : CallAdapter.Factory(){
    override fun get(
        returnType: Type,
        annotation: Array<out Annotation?>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != CustomCall::class.java) {
            return null
        }
        check(returnType is ParameterizedType) {
            "Return type must be parameterized as CustomCall<Foo> or CustomCall<? extends Foo>"
        }
        val responseType = getParameterUpperBound(0, returnType)
        val callbackExecutor = retrofit.callbackExecutor()
        return ErrorHandlingCallAdapter<Any>(responseType, callbackExecutor)
    }
}