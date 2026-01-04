package com.kfouri.networking.di.service

import com.kfouri.networking.responsehandler.interceptedresponse.InterceptedResponse
import okhttp3.ResponseBody
import retrofit2.http.GET

interface TodoService {

    @GET("todos/1")
    suspend fun getTodo(): InterceptedResponse<ResponseBody>

}