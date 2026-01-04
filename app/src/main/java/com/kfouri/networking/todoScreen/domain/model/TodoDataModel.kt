package com.kfouri.networking.todoScreen.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TodoDataModel(
    @SerializedName(value = "userId")
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)