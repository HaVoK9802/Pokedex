package com.example.pokdex.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokdex.data.remote.PokeApi
import com.example.pokdex.utils.RequestStatus
import com.example.pokdex.data.models.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class PokemonDetailViewModel @Inject constructor(val pokemonApiService: PokeApi):ViewModel(){

    var pokeDetailStatus: RequestStatus<Pokemon> by mutableStateOf(RequestStatus.Loading())
    var height:Int by mutableStateOf(0)
    var weight:Int by mutableStateOf(0)
    //even if it was not state property, directly mutating this property through the vm instance(who's state is tracked)
    //will cause a recomposition. But it is not good practice to do it that way.
    var pokemonName:String by mutableStateOf("")
    var statResult:List<com.example.pokdex.data.models.Stat> by mutableStateOf(listOf())
    var types:List<com.example.pokdex.data.models.Type> by mutableStateOf(listOf())
    var imgUrl:String = ""



    fun fetchPokemonStats(name:String){

            viewModelScope.launch(Dispatchers.IO) {
                pokeDetailStatus = try {

                    val res = pokemonApiService.getPokemonDetails(name)
                    withContext(Dispatchers.Main){
                        statResult = res.stats
                        types = res.types
                        height = res.height
                        weight = res.weight
                        pokemonName = res.name
                        imgUrl = res.sprites.front_default

                        //state values deal with UI, thus switching to main thread.
                    }
                    RequestStatus.Success(res)
                } catch (e: IOException) {
                    RequestStatus.Error("Not Able to fetch")
                }
            }

    }
}