package com.example.pokdex.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokdex.data.remote.LIMIT
import com.example.pokdex.data.remote.OFFSET

import com.example.pokdex.data.remote.PokemonApi
import com.example.pokdex.data.remote.PokemonCardDetails
import com.example.pokdex.data.remote.RequestStatus
import com.example.pokdex.data.remote.responses.Pokemon
import com.example.pokdex.data.remote.responses.PokemonList
import com.example.pokdex.data.remote.responses.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.openjdk.tools.javac.jvm.Items
import java.io.IOException

class PokemonViewModel:ViewModel() {
            var pokemonListStatus:RequestStatus<PokemonList> by mutableStateOf(RequestStatus.Loading<PokemonList>())
//            private var results:List<Result> = mutableListOf<Result>().toMutableStateList()
//            var resultsCopy:MutableList<Result> = mutableListOf<Result>().toMutableStateList()
    //only addition, deletion and updation within these lists will trigger recomposition

    private var results:List<Result> by mutableStateOf(listOf())
    var resultsCopy:MutableList<Result> by mutableStateOf(mutableStateListOf())
    //I need to track both state change(when i directly assign necessaries from result to resultCopy i.e for startsWith)
    // And I need to add() some partial matched results without disturbing the result of startsWith.
    // But add() will not track internal changes in the MutableList(only tracks assignment or instance change)
    // so to solve this corner case also, mutableStateList() helps. Because it tracks changes to the List in terms of additions,deletions,updates

            var searchQueryValue:String by mutableStateOf("")
            init{
                getPokemonList();
            }
            fun getPokemonList(){
                viewModelScope.launch(Dispatchers.IO){
                     pokemonListStatus = try {
                         val res  = PokemonApi.retrofitService.getPokemonList(
                             OFFSET, LIMIT)

//                         results = res.results.toMutableStateList()
//                         resultsCopy = results.toMutableStateList()

                         results = res.results
                         resultsCopy = results.toMutableStateList()

                         RequestStatus.Success(res)
                     }
                     catch (e:IOException){
                        RequestStatus.Error("Failed to fetch")
                     }
                }
            }

            fun liveSearch(query:String){
                viewModelScope.launch(Dispatchers.Default){
                    if(query==""){
//                        resultsCopy.clear()
//                        for(everyItem in results.toMutableList()){
//                            resultsCopy.add(everyItem)
//                        }
                        resultsCopy=results.toMutableStateList()
                    }
                    else{
//                        resultsCopy.clear()
                        if(results.filter { it.name.startsWith(query) }
                                .isNotEmpty()) {
//                            for (everyItem in results.toMutableList()
//                                .filter { it.name.startsWith(query)}) {
//                                resultsCopy.add(everyItem)
//                            }
                            resultsCopy = results.filter { it.name.startsWith(query) }.toMutableStateList()
                            for (everyItem in results.filter{ query in it.name }.sortedBy { it.name }) {
                                if(!resultsCopy.contains(everyItem))
                                resultsCopy.add(everyItem)
                            }
                        }
                        else {
//                            for (everyItem in results.toMutableList().filter{ query in it.name }) {
//                                resultsCopy.add(everyItem)
//                            }
                            resultsCopy = results.filter{ query in it.name }.sortedBy { it.name }.toMutableStateList()
                        }
//                        resultsCopy.sortBy{it.name}

                    }
                }
            }
            fun getPokemonCardDetails(name: String,results: List<Result>){

            }


}