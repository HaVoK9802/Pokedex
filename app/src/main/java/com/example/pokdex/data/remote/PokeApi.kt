package com.example.pokdex.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset")offset:Int,
        @Query("limit")limit:Int
    ): com.example.pokdex.data.models.PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(
       @Path("name")name:String
    ): com.example.pokdex.data.models.Pokemon

}
