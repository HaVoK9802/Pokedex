package com.example.pokdex.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokdex.data.remote.LIMIT
import com.example.pokdex.data.remote.OFFSET
import com.example.pokdex.data.remote.PokemonApi
import com.example.pokdex.data.remote.RequestStatus
import com.example.pokdex.data.remote.responses.PokemonList
import com.example.pokdex.data.remote.responses.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class PokemonViewModel:ViewModel() {
            var pokemonListStatus:RequestStatus<PokemonList> by mutableStateOf(RequestStatus.Loading<PokemonList>())
            var results:List<Result> = mutableListOf<Result>()
            var resultsCopy:List<Result> = results.toMutableStateList()
            init{
//                getPokemonList()
//                viewModelScope.launch(Dispatchers.IO){
//                    setResults(PokemonApi.retrofitService.getPokemonList(
//                        OFFSET, LIMIT))
//                }
            }
            fun getPokemonList(){
                viewModelScope.launch(Dispatchers.IO){
                     pokemonListStatus = try {
                         val res  = PokemonApi.retrofitService.getPokemonList(
                             OFFSET, LIMIT)

                         results = res.results
                         resultsCopy = results.toMutableStateList()
                         RequestStatus.Success(res)
                     }
                     catch (e:IOException){
                        RequestStatus.Error("Failed to fetch")
                     }
                }
            }



}