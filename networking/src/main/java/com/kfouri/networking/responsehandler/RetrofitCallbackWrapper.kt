package com.kfouri.networking.responsehandler

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class RetrofitCallbackWrapper<T>: Callback<T> {

    abstract fun onCallResponse(call: Call<T>, response: Response<T>)
    abstract fun onCallFailure(call: Call<T>, t: Throwable)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        onCallResponse(call, response)
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onCallFailure(call, t)
    }
}
