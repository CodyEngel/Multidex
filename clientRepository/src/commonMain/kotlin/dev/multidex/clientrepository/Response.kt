package dev.multidex.clientrepository

sealed class Response<T> {
    data class Success<T>(val result: T): Response<T>()
    sealed class Error : Response<Nothing>() {
        object GenericError : Error()
    }
}