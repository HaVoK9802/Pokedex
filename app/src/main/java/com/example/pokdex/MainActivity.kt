package com.example.pokdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokdex.presentation.PokemonDetailViewModel
import com.example.pokdex.presentation.PokemonViewModel

import com.example.pokdex.ui.theme.PokédexTheme
import com.example.pokdex.presentation.views.PokemonDetailScreen
import com.example.pokdex.presentation.views.PokemonListScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            PokédexTheme {

                 val navController = rememberNavController()
                 val pokemonListViewModel:PokemonViewModel= hiltViewModel()
                 LaunchedEffect(Unit){
                     pokemonListViewModel.getPokemonList()
                 // for side-effects, takes in state dependencies. If provided with static value like Unit, it will run only on initial composition.
                 }

                 NavHost(
                     navController = navController,
                     startDestination = "pokemon_list_screen"
                 ){
                    composable(
                        route="pokemon_list_screen"
                    ){
                        PokemonListScreen(navController = navController, viewModel = pokemonListViewModel)
                    }
                    composable(
                        route="pokemon_detail_screen/{pokemonName}",
                        arguments = listOf(
                            navArgument(name = "pokemonName"){
                                type = NavType.StringType
                            }
                        )
                    ){
                        val pokemonName:String? = remember{it.arguments?.getString("pokemonName")}

                        val pokemonDetailViewModel:PokemonDetailViewModel = hiltViewModel()


                        LaunchedEffect(Unit){
                            pokemonDetailViewModel.fetchPokemonStats(name = pokemonName?:"")
                        }
                        PokemonDetailScreen(navController = navController,pokemonDetailViewModel = pokemonDetailViewModel )
                    }
                 }
            }
        }
    }
}



