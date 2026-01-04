package com.kfouri.networking.todoScreen.domain

import com.kfouri.networking.todoScreen.domain.model.TodoDataModel
import com.kfouri.networking.util.Resource
import javax.inject.Inject

class GetTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(): Resource<TodoDataModel> {
        return repository.getTodo()
    }
}