package com.inspiredCoda.ktorclientyt.data

sealed class NetworkEvents {
    data class Success<T>(val data: T) : NetworkEvents()
    data class Failure(val message: String) : NetworkEvents()
}
