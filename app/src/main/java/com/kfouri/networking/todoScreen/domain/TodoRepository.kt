package com.kfouri.networking.todoScreen.domain

import com.kfouri.networking.todoScreen.domain.model.TodoDataModel
import com.kfouri.networking.util.Resource

interface TodoRepository {
    suspend fun getTodo(): Resource<TodoDataModel>
}