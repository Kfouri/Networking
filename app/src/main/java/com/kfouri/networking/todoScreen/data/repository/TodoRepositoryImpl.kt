package com.kfouri.networking.todoScreen.data.repository

import com.kfouri.networking.di.service.TodoService
import com.kfouri.networking.todoScreen.domain.TodoRepository
import com.kfouri.networking.todoScreen.domain.model.TodoDataModel
import com.kfouri.networking.util.Resource
import com.kfouri.networking.util.deserializer.MapResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val service: TodoService
): TodoRepository {
    override suspend fun getTodo(): Resource<TodoDataModel> {
        return withContext(Dispatchers.IO) {
            val response = service.getTodo()
            MapResponse().map(response, TodoDataModel::class.java)
        }
    }
}