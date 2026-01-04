package com.kfouri.networking.todoScreen.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kfouri.networking.todoScreen.domain.GetTodoUseCase
import com.kfouri.networking.util.Status.SUCCESS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodoUseCase: GetTodoUseCase
): ViewModel() {
    private val _viewState = MutableStateFlow<TodoViewState>(TodoViewState.Loading)
    val viewState: StateFlow<TodoViewState> = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            getTodo()
        }
    }

    private suspend fun getTodo() {
        _viewState.emit(TodoViewState.Loading)
        val result = getTodoUseCase()
        when (result.status) {
           SUCCESS -> {
               result.data?.let { data ->
                   _viewState.emit(TodoViewState.Success(data))
               } ?: run {
                   _viewState.emit(TodoViewState.Error(result.status, "99", "Data Empty"))
               }
           }
           else -> {
               _viewState.emit(TodoViewState.Error(result.status, result.errorCode, result.errorMessage))
           }
        }
    }
}
