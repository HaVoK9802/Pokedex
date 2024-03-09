package com.example.pokdex.ui.views



import android.inputmethodservice.Keyboard
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.ColumnScopeInstance.align

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.core.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.Coil
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.pokdex.R
import com.example.pokdex.data.remote.IMAGE_BASE_URL
import com.example.pokdex.data.remote.RequestStatus
import com.example.pokdex.data.remote.responses.PokemonList
import com.example.pokdex.domain.PokemonViewModel
import com.example.pokdex.ui.theme.LightBlue
import org.openjdk.tools.javac.util.RichDiagnosticFormatter


@Composable
fun PokemonListScreen(navController: NavController, viewModel: PokemonViewModel) {

   Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = "Pokemon Logo"
            )
            Spacer(modifier = Modifier.height(20.dp))


            SearchBar(viewModel = viewModel)

            Spacer(modifier = Modifier.height(30.dp))
        }

       Box(modifier = Modifier
           .fillMaxWidth()
           .offset(0.dp, 210.dp)) {

            when (viewModel.pokemonListStatus) {
                is RequestStatus.Loading -> CircularProgressIndicator(modifier = Modifier
                    .fillMaxSize()
                    .padding(100.dp)
                    .align(Alignment.Center))
                is RequestStatus.Success -> {}
                is RequestStatus.Error -> Text(text = "Failed to fetch")
            }
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .background(Color.White),
                columns = GridCells.Fixed(2)
            ) {
                items(viewModel.resultsCopy) { load ->
//                    Text(text = load.name+load.url +load.pokeId.toString())
                    Box(modifier = Modifier
                        .padding(10.dp)
                        .shadow(10.dp, shape = RoundedCornerShape(10.dp), spotColor = Color.Black)
                        .offset(0.dp, 1.dp)
                    )
                    {
                        PokeCard(navController = navController, name = load.name , pokeId = load.pokeId)
                    }
//                    PokeCard(navController = navController, name = load.name , pokeId = load.pokeId)
                }

            }
        }
        Pokeball()
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PokemonListScreenPreview() {
    PokemonListScreen(navController = rememberNavController(), viewModel = PokemonViewModel())
}

@Composable
fun Pokeball() {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(51.dp)
            .background(Color.Transparent).offset(0.dp,180.dp), contentAlignment = Alignment.Center
    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(20.dp)
//                .background(Color.Red)
//                .align(Alignment.TopCenter)
//        ) {
//
//        }
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth()
                .background(Color.Black)
        )
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .clip(CircleShape)
                .background(Color.Black),
            contentAlignment = Alignment.Center

        ) {
            Box(
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(27.dp)
                        .height(27.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
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
fun PokeballPreview() {
    Pokeball()
}


@Composable
fun SearchBar(viewModel: PokemonViewModel) {

        OutlinedTextField(
            value = viewModel.searchQueryValue,
            onValueChange = {
                viewModel.searchQueryValue = it.lowercase()
                viewModel.liveSearch(it)
            },
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            maxLines = 1,
            singleLine = true,
            placeholder = { Text(text = "Enter Pokemon...")},
            shape = RoundedCornerShape(8.dp),
            leadingIcon ={ Icon(imageVector = Icons.Default.Search, contentDescription ="Search icon" )
            },
            colors = TextFieldDefaults.
            colors(unfocusedContainerColor = Color.Black,
                focusedContainerColor = Color.Black,
                unfocusedTextColor = Color.LightGray,
                focusedTextColor = Color.LightGray),
            )




}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(viewModel = viewModel())
}

@Composable
fun ListOfPokemon() {

}

@Composable
fun PokeCard(navController: NavController,name:String,pokeId:Int){
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(0.75f)
            .background(Color(255, 255, 255, 255))
//            .border(1.dp, color = Color.Red, shape = RoundedCornerShape(10.dp))
            //43,109,181
//            .border(
//                0.dp,
//                brush = Brush.verticalGradient(listOf(Color(43,109,181),Color(43,109,181),)),
//                shape = RoundedCornerShape(10.dp)
//            )
            .clickable { navController.navigate("pokemon_detail_screen/${name}") }

        , contentAlignment = Alignment.Center
    ){

        SubcomposeAsyncImage(
            model = IMAGE_BASE_URL+pokeId.toString()+".png",
            contentDescription = "cec",
            modifier = Modifier.fillMaxSize()
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                CircularProgressIndicator(modifier = Modifier.padding(60.dp))
            }
            else {
                SubcomposeAsyncImageContent(modifier = Modifier
                    .padding(5.dp)
                    .padding(bottom = 30.dp))
            }
        }

       Text(text = name,
           modifier = Modifier
               .align(Alignment.BottomCenter)
               .padding(20.dp)
           , fontSize = TextUnit(15f,TextUnitType.Sp)
//           , color = Color(43,109,181)
       )
    }
}

@Preview
@Composable
fun PokeCardPreview(){
    PokeCard(navController = rememberNavController(), name = "Pokemon", pokeId = 2)
}



