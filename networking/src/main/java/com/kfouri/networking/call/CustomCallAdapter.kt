package com.kfouri.networking.call

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor

class CustomCallAdapter<T>(call: Call<T>?, callbackExecutor: Executor?): CustomCall<T> {

    private var call: Call<T>? = null
    private var callbackExecutor: Executor? = null

    init {
        this.call = call
        this.callbackExecutor = callbackExecutor
    }

    override fun cancel() {
        call?.cancel()
    }

    override fun enqueue(callback: CustomCallback<T>?) {
        call?.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T?>, response: Response<T?>) {
                when (response.code()) {
                    in 200 .. 299 -> {
                        callback?.success(response as Response<T>)
                    }
                    401, 406, 408 -> {
                        callback?.unauthenticated(response as Response<T>)
                    }
                    in 400 .. 499 -> {
                        callback?.clientError(response as Response<T>)
                    }
                    in 500 .. 599 -> {
                        callback?.serverError(response as Response<T>)
                    }
                    else -> {
                        callback?.unexpectedError(RuntimeException("Unexpected response $response"))
                    }
                }
            }

            override fun onFailure(call: Call<T?>, t: Throwable) {
                if (t is IOException) {
                    callback?.networkError(t)
                } else {
                    callback?.unexpectedError(t)
                }
            }

        })
    }

    override fun clone(): CustomCall<T>? {
        return CustomCallAdapter(call?.clone(), callbackExecutor)
    }
}