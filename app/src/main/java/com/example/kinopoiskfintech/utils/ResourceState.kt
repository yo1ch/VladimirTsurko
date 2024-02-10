package com.example.kinopoiskfintech.utils

sealed class ResourceState<out T> {

    object Loading : ResourceState<Nothing>()

    data class Content<T>(val content: T) : ResourceState<T>()

    data class Error<T>(val message: String? = null) : ResourceState<T>()
}