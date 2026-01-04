package com.kfouri.networking.util

import androidx.annotation.Keep

@Keep
object Status {
    const val SUCCESS = "SUCCESS"
    const val NETWORK_ERROR = "NETWORK_ERROR"
    const val UNAUTHORIZED = "UNAUTHORIZED"
    const val SERVER_ERROR = "SERVER_ERROR"
    const val RETRY_ERROR = "RETRY_ERROR"
    const val API_ERROR = "API_ERROR"
    const val UNKNOWN_ERROR = "UNKNOWN_ERROR"
    const val MAINTENANCE = "MAINTENANCE"
}