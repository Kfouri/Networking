package com.kfouri.networking.util

import androidx.annotation.Keep

@Keep
data class Resource<out T>(
    val status: String,
    val errorCode: String?,
    val errorMessage: String?,
    val data: T?
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                status = Status.SUCCESS,
                errorCode = null,
                errorMessage = null,
                data = data
            )
        }

        fun <T> error(
            status: String,
            errorCode: String? = null,
            errorMessage: String? = null
        ): Resource<T> {
            return Resource(
                status = status,
                errorCode = errorCode,
                errorMessage = errorMessage,
                data = null
            )
        }

        fun <T> exception(
            data: T?
        ): Resource<T> {
            return Resource(
                status = Status.NETWORK_ERROR,
                errorCode = null,
                errorMessage = null,
                data = data
            )
        }
    }
}