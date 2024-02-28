package com.example.pokdex.ui.views

import android.inputmethodservice.Keyboard
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.ColumnScopeInstance.align

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pokdex.R
import com.example.pokdex.data.remote.RequestStatus
import com.example.pokdex.data.remote.responses.PokemonList
import com.example.pokdex.domain.PokemonViewModel
import com.example.pokdex.ui.theme.LightBlue
import org.openjdk.tools.javac.util.RichDiagnosticFormatter


@Composable
fun PokemonListScreen(navController: NavController, viewModel: PokemonViewModel) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = "Pokemon Logo"
            )
            SearchBar(viewModel = viewModel)
        }

        Pokeball()
        val gradient = Brush.verticalGradient( // Create a vertical gradient
            listOf(Color.Blue, Color.Red), // List of colors
            startY = 0f, // Starting position (0f for top)
            endY = 1f // Ending position (1f for bottom)
        )

        Column(modifier = Modifier.fillMaxWidth() ) {

            when(viewModel.pokemonListStatus){
                is RequestStatus.Loading -> Text(text = "Loading...")
                is RequestStatus.Success ->{}
                is RequestStatus.Error -> Text(text = "Failed to fetch")
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(viewModel.resultsCopy){
                    load -> Text(text = load.name)
                }

            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PokemonListScreenPreview() {
    PokemonListScreen(navController = rememberNavController(), viewModel = PokemonViewModel())
}

@Composable
fun Pokeball(){


    Box(modifier = Modifier
        .fillMaxWidth()
        .height(51.dp)

        .shadow(
            elevation = 10.dp,
            ambientColor = Color.White,
            spotColor = Color.White
        )
        ,contentAlignment = Alignment.Center){
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .background(Color.Red)
            .align(Alignment.TopCenter)
        ){

        }
        Spacer(modifier = Modifier
            .height(12.dp)
            .fillMaxWidth()
            .background(Color.Black))
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .clip(CircleShape)
                .background(Color.Black),
            contentAlignment = Alignment.Center

        ){
            Box(
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ){
                Box(
                    modifier = Modifier
                        .width(27.dp)
                        .height(27.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ){
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun PokeballPreview(){
    Pokeball()
}


@Composable
fun SearchBar(viewModel: PokemonViewModel) {

    Row(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        TextField(
            value = viewModel.searchQueryValue,
            onValueChange = {
                viewModel.searchQueryValue = it.lowercase()
                viewModel.liveSearch(it)
            },
            modifier = Modifier,
            maxLines = 1,
            singleLine = true
        )
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(viewModel = viewModel())
}

@Composable
fun ListOfPokemon() {

}




