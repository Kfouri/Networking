package com.kfouri.networking.responsehandler.interceptedresponse

import androidx.annotation.Keep
import okhttp3.Headers
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.Response

@Keep
sealed class InterceptedResponse<out T : Any> {
    data class Success<out T : Any>(
        val data: T?,
        val code: Int,
        val headers: Headers,
        private val rawResponse: Response<T>
    ) : InterceptedResponse<T>()

    data class ApiError<out T : Any>(
        val errorBody: ResponseBody?,
        val code: Int,
        val headers: Headers,
        private val rawResponse: Response<T>
    ): InterceptedResponse<T>()

    data class NetworkError(
        val error: IOException
    ): InterceptedResponse<Nothing>()

    data class UnknownError(
        val error: Throwable?
    ): InterceptedResponse<Nothing>()
}