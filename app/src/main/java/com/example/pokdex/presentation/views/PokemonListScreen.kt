package com.example.pokdex.presentation.views



import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.pokdex.R
import com.example.pokdex.data.remote.IMAGE_BASE_URL
import com.example.pokdex.data.remote.RequestStatus
import com.example.pokdex.presentation.PokemonViewModel


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

            Row(modifier = Modifier
//                .padding(horizontal = 5.dp),
                ,
                verticalAlignment = Alignment.CenterVertically
            ){
                SearchBar(viewModel = viewModel)
//                IconButton(onClick = {},
//                    modifier = Modifier
//                        .clip(RoundedCornerShape(8.dp))
//                        .background(Color.Black)
//                        .padding(3.5.dp)
//                ) {
//                   Icon(painter = painterResource(id = R.drawable.sort_alphabetical_ascending_variant),
//                       contentDescription ="ascending", tint = Color.White )
//                }
            }


            Spacer(modifier = Modifier.height(30.dp))
        }

       Box(modifier = Modifier
           .fillMaxWidth()
//           .offset(0.dp, 0.dp)
       )
       {

            when (viewModel.pokemonListStatus) {
                is RequestStatus.Loading -> CircularProgressIndicator(modifier = Modifier
                    .fillMaxSize()
                    .padding(100.dp)
                    .align(Alignment.Center)
                    .offset(0.dp, 210.dp)
                )
                is RequestStatus.Success -> {}
                is RequestStatus.Error -> Text(text = "Failed to fetch")
            }
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(top = 210.dp)
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
                        PokeCard(viewModel = viewModel,navController = navController, name = load.name , pokeId = load.pokeId)
                    }
//                    PokeCard(navController = navController, name = load.name , pokeId = load.pokeId)
                }

            }
        }
        Pokeball()
    }
}




@Composable
fun Pokeball() {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(51.dp)
            .background(Color.Transparent)
            .offset(0.dp, 180.dp), contentAlignment = Alignment.Center
    ) {

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



@Composable
fun SearchBar(viewModel: PokemonViewModel) {

        OutlinedTextField(
            value = viewModel.searchQueryValue,
            onValueChange = {
                viewModel.searchQueryValue = it.lowercase()
                viewModel.liveSearch(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
//            .padding(end = 20.dp),
            maxLines = 1,
            singleLine = true,
            placeholder = { Text(text = "Enter Pokemon...") },
            shape = RoundedCornerShape(8.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon",
                    modifier = Modifier.clickable {  })
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Close, tint = Color.White,
                    contentDescription = "clear search", modifier = Modifier.padding(end = 40.dp).clickable { viewModel.clearSearch() })
                IconButton(onClick = { viewModel.sortingFeature(!viewModel.sortingToggle) }, modifier = Modifier.padding(start = 35.dp)) {
                    if(viewModel.sortingToggle==true) {
                        Icon(
                            painter = painterResource(id = R.drawable.sort_alphabetical_ascending),
                            contentDescription = "ascending", tint = Color.White,
                            modifier = Modifier.fillMaxSize().padding(2.dp)
                        )
                    }
                    else if(viewModel.sortingToggle==false){
                        Icon(
                            painter = painterResource(id = R.drawable.sort_alphabetical_descending),
                            contentDescription = "descending", tint = Color.White,
                            modifier = Modifier.fillMaxSize().padding(2.dp)
                        )
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Black,
                focusedContainerColor = Color.Black,
                unfocusedTextColor = Color.LightGray,
                focusedTextColor = Color.LightGray,
                unfocusedIndicatorColor = Color.Black,
                focusedIndicatorColor = Color.DarkGray,
            ),

        )




}



@Composable
fun PokeCard(viewModel: PokemonViewModel,navController: NavController,name:String,pokeId:Int){
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(0.75f)
            .background(Color(255, 255, 255, 255))
            .clickable {

                if (!viewModel.cardTapped) {
                    viewModel.cardTapped = true;
                    viewModel.revertCardTapped()
                    navController.navigate("pokemon_detail_screen/${name}")
                }
            }

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
//    PokeCard(viewModel = hiltViewModel(),navController = rememberNavController(), name = "Pokemon", pokeId = 2)
}

@Preview
@Composable
fun PokeballPreview() {
//    Pokeball()
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(viewModel = viewModel())
}


@Preview
@Composable
fun PokemonListScreenPreview() {
//    PokemonListScreen(navController = rememberNavController(), viewModel = PokemonViewModel())
}