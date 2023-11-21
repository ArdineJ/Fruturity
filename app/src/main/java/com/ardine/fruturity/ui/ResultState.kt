package com.ardine.fruturity.ui

sealed class ResultState<out T: Any?> {

    data class Success<out T: Any>(val data: T) : ResultState<T>()
    data class Error(val errorMessage: String) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()
}