package com.kfouri.networking.util.deserializer

import androidx.annotation.Keep
import com.kfouri.networking.responsehandler.interceptedresponse.InterceptedResponse
import com.kfouri.networking.util.Constants.EMPTY_JSON
import com.kfouri.networking.util.Constants.MAINTENANCE_CODE
import com.kfouri.networking.util.Constants.NO_BODY_HTTP_CODE
import com.kfouri.networking.util.Resource
import com.kfouri.networking.util.Status.MAINTENANCE
import com.kfouri.networking.util.Status.NETWORK_ERROR
import com.kfouri.networking.util.Status.SERVER_ERROR
import okhttp3.ResponseBody

@Keep
class MapResponse {

    fun <T> map(
        response: InterceptedResponse<ResponseBody>,
        classType: Class<T>
    ): Resource<T> {
        return when (response) {
            is InterceptedResponse.Success -> {
                try {
                    val gson = GsonUtil.getGsonBuilder()
                    val result = if (response.code == NO_BODY_HTTP_CODE) {
                        gson.fromJson(EMPTY_JSON, classType)
                    } else {
                        response.data?.charStream()?.let { stream ->
                            gson.fromJson(stream, classType)
                        }
                    }
                    Resource.success(result)
                } catch (e: Exception) {
                    Resource.error(
                        status = SERVER_ERROR,
                        errorCode = response.code.toString(),
                        errorMessage = e.message
                    )
                }
            }
            is InterceptedResponse.ApiError -> {
                if (response.code == MAINTENANCE_CODE) {
                    Resource.error(MAINTENANCE)
                } else {
                    Resource.error(
                        status = SERVER_ERROR,
                        errorCode = response.code.toString(),
                        errorMessage = (response.errorBody as ResponseBody).toString()
                    )
                }
            }
            is InterceptedResponse.NetworkError -> {
                Resource.error(
                    status = NETWORK_ERROR,
                    errorCode = response.error.cause?.message,
                    errorMessage = response.error.message
                )
            }
            is InterceptedResponse.UnknownError -> {
                Resource.error(
                    status = SERVER_ERROR,
                    errorCode = null,
                    errorMessage = response.error?.message,
                )
            }
        }
    }
}