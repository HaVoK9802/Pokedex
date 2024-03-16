package com.example.pokdex.presentation


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokdex.data.remote.LIMIT
import com.example.pokdex.data.remote.OFFSET
import com.example.pokdex.data.remote.PokeApi
import com.example.pokdex.data.remote.RequestStatus
import com.example.pokdex.data.remote.models.PokemonList
import com.example.pokdex.data.remote.models.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class PokemonViewModel @Inject constructor(val pokemonApiService: PokeApi) : ViewModel() {
    var pokemonListStatus: RequestStatus<PokemonList> by mutableStateOf(RequestStatus.Loading())

    private var results: List<Result> =listOf()
    var resultsCopy: MutableList<Result> = mutableStateListOf()

    var searchQueryValue: String by mutableStateOf("")

    var cardTapped = false;



//    init {
//        getPokemonList()
////       not a good practice, only for initializations
//    }

     fun getPokemonList() {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonListStatus = try {
                val res = pokemonApiService.getPokemonList(
                    OFFSET, LIMIT
                )

                results = res.results
                results.map { it.pokeId = calculatePokeId(it.url) }
                withContext(Dispatchers.Main){
                //accessing UI state should not be done on IO thread.
                //if you do so, an exception is thrown stating that
                    //FATAL EXCEPTION: DefaultDispatcher-worker-1
                    //Process: com.example.pokdex, PID: 27664
                    //java.lang.IllegalStateException: Reading a state that was created after the snapshot was taken or in a snapshot that has not yet been applied
                    // in simple words you are trying to access/mutate state (without sync with UI in main thread)
                    // after the changes have already been decided and ready to compose
//                resultsCopy = results.toMutableStateList()
                  resultsCopy.addAll(results)
                }
                RequestStatus.Success(res)
            } catch (e: IOException) {
                RequestStatus.Error("Failed to fetch")
            }
        }
    }

    fun liveSearch(query: String) {
        viewModelScope.launch(Dispatchers.Default) {

            val filterByStartingStringMatch = results.filter { it.name.startsWith(query) }
            val filterByRelevance = results.filter { query in it.name }


            if (query == "") {

                resultsCopy.clear()
                resultsCopy.addAll(results)


            } else {

                if (filterByStartingStringMatch.isNotEmpty()) {

                    resultsCopy.clear()
                    resultsCopy.addAll(filterByStartingStringMatch)
                    for(pokemon in filterByRelevance.sortedBy { it.name }){
                        if(!resultsCopy.contains(pokemon)){
                            resultsCopy.add(pokemon)
                        }
                    }

                } else {

                    resultsCopy.clear()
                    resultsCopy.addAll(filterByRelevance.sortedBy { it.name })

                }


            }
        }
    }

    fun calculatePokeId(url:String):Int{
        val rev = url.reversed()
        var idx = 1
        var numSys = 1
        var pokeId = 0;
        while(rev[idx] !='/'){
            pokeId += numSys * rev[idx].digitToInt()
            numSys*=10;
            idx++;
        }
        return pokeId;
    }

    fun revertCardTapped(){
        viewModelScope.launch(Dispatchers.Main){
            delay(700)
            cardTapped = false
        }
    }



}