package com.example.pokdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokdex.domain.PokemonDetailViewModel
import com.example.pokdex.domain.PokemonViewModel

import com.example.pokdex.ui.theme.PokédexTheme
import com.example.pokdex.ui.views.PokemonDetailScreen
import com.example.pokdex.ui.views.PokemonListScreen
import androidx.lifecycle.viewmodel.compose.viewModel as viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            PokédexTheme {

                 val navController = rememberNavController()
                 val PokemonListViewModel:PokemonViewModel= viewModel()
                 LaunchedEffect(Unit){
                     PokemonListViewModel.getPokemonList()
                 // for side-effects, takes in state dependencies. If provided with static value like Unit, it will run only on initial composition.
                 }

                 NavHost(
                     navController = navController,
                     startDestination = "pokemon_list_screen"
                 ){
                    composable(
                        route="pokemon_list_screen"
                    ){
                        PokemonListScreen(navController = navController, viewModel = PokemonListViewModel)
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

                        val pokemonDetailViewModel:PokemonDetailViewModel = viewModel();

                        LaunchedEffect(Unit){
                            pokemonDetailViewModel.fetchPokemonStats(name = pokemonName!!)
                        }
                        PokemonDetailScreen(navController = navController,pokemonDetailViewModel = pokemonDetailViewModel )
                    }
                 }
            }
        }
    }
}

//@Preview
//@Composable
//fun APIResults(){
//     Surface(modifier = Modifier.fillMaxSize()){
//         val viewModel:PokemonViewModel= viewModel()
//         viewModel.getPokemonList()
//
//         when(viewModel.pokemonListStatus){
//             is RequestStatus.Loading -> Text(text = "loading...")
//             is RequestStatus.Success ->  Text(text = viewModel.resultsCopy[5].name)
//             is RequestStatus.Error -> Text(text = "rtt")
//         }
//
//     }
//}

