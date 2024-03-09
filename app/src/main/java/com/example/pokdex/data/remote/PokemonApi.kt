package com.example.pokdex.data.remote

import com.example.pokdex.data.remote.models.Pokemon
import com.example.pokdex.data.remote.models.PokemonList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

val retrofit = Retrofit.Builder()
                      .baseUrl(BASE_URL)
                      .addConverterFactory(GsonConverterFactory.create())
                      .build()

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset")offset:Int,
        @Query("limit")limit:Int
    ):PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(
       @Path("name")name:String
    ):Pokemon

}

object PokemonApi{
     val retrofitService:PokeApi  by lazy{ retrofit.create(PokeApi::class.java)}
}