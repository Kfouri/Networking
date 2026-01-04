package com.kfouri.networking.responsehandler.interceptedresponse

import com.kfouri.networking.responsehandler.RetrofitCallbackWrapper
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

internal class InterceptedResponseCall<S : Any>(
    private val delegate: Call<S>
) : Call<InterceptedResponse<S>> {

    override fun enqueue(callback: Callback<InterceptedResponse<S>?>) {
        return delegate.enqueue(object : RetrofitCallbackWrapper<S>() {

            override fun onCallResponse(call: Call<S>, response: Response<S>) {
                with(response) {
                    if (isSuccessful) {
                        val success = InterceptedResponse.Success(body(), code(), headers(), this)
                        callback.onResponse(
                            this@InterceptedResponseCall as Call<InterceptedResponse<S>?>,
                            Response.success(success)
                        )
                    } else {
                        callback.onResponse(
                            this@InterceptedResponseCall as Call<InterceptedResponse<S>?>,
                            Response.success(
                                InterceptedResponse.ApiError(
                                    errorBody(),
                                    code(),
                                    headers(),
                                    this
                                )
                            )
                        )
                    }
                }
            }

            override fun onCallFailure(call: Call<S>, t: Throwable) {
                val networkResponse = when (t) {
                    is IOException -> InterceptedResponse.NetworkError(t)
                    else -> InterceptedResponse.UnknownError(t)
                }
                callback.onResponse(this@InterceptedResponseCall as Call<InterceptedResponse<S>?>, Response.success(networkResponse))
            }

        })

    }

    override fun execute(): Response<InterceptedResponse<S>?> {
        throw UnsupportedOperationException("InterceptedResponseCall doesn't support execute")
    }

    override fun isExecuted() = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled() = delegate.isCanceled

    override fun clone() = InterceptedResponseCall(delegate.clone())

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

}
