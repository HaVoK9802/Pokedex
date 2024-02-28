package com.example.pokdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavArgument
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokdex.data.remote.RequestStatus
import com.example.pokdex.data.remote.responses.PokemonList
import com.example.pokdex.domain.PokemonViewModel

import com.example.pokdex.ui.theme.PokédexTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.viewmodel.compose.viewModel as viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokédexTheme {
                 APIResults()
                 val navController = rememberNavController()

                 NavHost(
                     navController = navController,
                     startDestination = "pokemon_list_screen"
                 ){
                    composable(
                        route="pokemon_list_screen"
                    ){

                    }
                    composable(
                        route="pokemon_detail_screen/{dominantColor}/{pokemonName}",
                        arguments = listOf(
                            navArgument(name = "dominantColor"){
                                type = NavType.IntType
                            },
                            navArgument(name = "pokemonName"){
                                type = NavType.StringType
                            }
                        )
                    ){
                        val dominantColor = remember{it.arguments?.getInt("dominantColor")}
                        val pokemonName = remember{it.arguments?.getString("pokemonName")}

                    }
                 }
            }
        }
    }
}

@Preview
@Composable
fun APIResults(){
     Surface(modifier = Modifier.fillMaxSize()){
         val viewModel:PokemonViewModel= viewModel()
         viewModel.getPokemonList()
         when(viewModel.pokemonListStatus){
             is RequestStatus.Loading -> Text(text = "loading...")
             is RequestStatus.Success ->  Text(text = viewModel.resultsCopy[5].name)
             is RequestStatus.Error -> Text(text = "rtt")
         }

     }
}

