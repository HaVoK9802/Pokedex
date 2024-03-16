package com.example.pokdex.data.remote

sealed class RequestStatus<T> {
    data class Success<T>(val data:T): RequestStatus<T>(){

    }
    class Loading<T>:RequestStatus<T>()
    class Error<T>(val data:String):RequestStatus<T>()
}