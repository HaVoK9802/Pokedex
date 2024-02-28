package com.example.pokdex.data.remote

import com.example.pokdex.data.remote.responses.Pokemon
import com.example.pokdex.data.remote.responses.PokemonList

sealed class RequestStatus<T> {
    data class Success<T>(val data:T): RequestStatus<T>(){

    }
    class Loading<T>:RequestStatus<T>()
    class Error<T>(val data:String):RequestStatus<T>()
}