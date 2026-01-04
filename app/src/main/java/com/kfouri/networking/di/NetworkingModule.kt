package com.kfouri.networking.di

import com.kfouri.networking.HttpClient
import com.kfouri.networking.di.service.TodoService
import com.kfouri.networking.responsehandler.interceptedresponse.InterceptedResponseAdapterFactory
import com.kfouri.networking.todoScreen.data.repository.TodoRepositoryImpl
import com.kfouri.networking.todoScreen.domain.GetTodoUseCase
import com.kfouri.networking.todoScreen.domain.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    @Singleton
    fun provideRetrofit(): TodoService {
        return HttpClient
            .getClient()
            .newBuilder()
            .addCallAdapterFactory(InterceptedResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoService::class.java)
    }

    @Provides
    fun getTodoUseCase(repository: TodoRepository): GetTodoUseCase {
        return GetTodoUseCase(repository)
    }


}

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModules{

    @Binds
    fun provideTodoRepository(repository: TodoRepositoryImpl): TodoRepository

}