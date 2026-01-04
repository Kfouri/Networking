package com.kfouri.networking.call

interface CustomCall<T> {
    fun cancel()
    fun enqueue(callback: CustomCallback<T>?)
    fun clone(): CustomCall<T>?
}