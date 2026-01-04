package com.kfouri.networking.responsehandler.callback

import com.kfouri.networking.responsehandler.RetrofitCallbackWrapper
import retrofit2.Call
import retrofit2.Response

class RetrofitCallWrapperImpl<T>(val call: Call<T>): RetrofitCallWrapper<T> {
    override fun cancel() = call.cancel()

    override fun enqueue(callback: RetrofitCallbackWrapper<T>) {
        call.enqueue(callback)
    }

    override fun execute(): Response<T> {
        return call.execute()
    }
}