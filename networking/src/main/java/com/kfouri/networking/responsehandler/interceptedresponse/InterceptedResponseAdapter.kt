package com.kfouri.networking.responsehandler.interceptedresponse

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class InterceptedResponseAdapter<S: Any>(
    private val successType: Type
): CallAdapter<S, Call<InterceptedResponse<S>>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<InterceptedResponse<S>> {
        return InterceptedResponseCall(call)
    }

}