package com.kfouri.networking.todoScreen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kfouri.networking.todoScreen.ui.viewmodel.TodoViewModel
import com.kfouri.networking.todoScreen.ui.viewmodel.TodoViewState

@Composable
fun TodoScreen(
    viewModel: TodoViewModel = hiltViewModel()
) {
    val viewState = viewModel.viewState.collectAsState()
    when (val state = viewState.value) {
        is TodoViewState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is TodoViewState.Success -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Title: ${state.data.title}")
            }
        }
        is TodoViewState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${state.message}")
            }
        }
    }
}