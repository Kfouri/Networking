package com.kfouri.networking.call

import okio.IOException
import retrofit2.Response

interface CustomCallback<T> {
    fun success(response: Response<T>)
    fun unauthenticated(response: Response<T>)
    fun serverError(response: Response<T>)
    fun clientError(response: Response<T>)
    fun networkError(e: IOException)
    fun unexpectedError(t: Throwable)
}