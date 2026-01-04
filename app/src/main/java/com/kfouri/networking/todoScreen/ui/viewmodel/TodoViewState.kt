package com.kfouri.networking.todoScreen.ui.viewmodel

import com.kfouri.networking.todoScreen.domain.model.TodoDataModel

sealed class TodoViewState {
    data object Loading: TodoViewState()
    data class Success(val data: TodoDataModel): TodoViewState()
    data class Error(val status: String, val code: String?, val message: String?): TodoViewState()
}