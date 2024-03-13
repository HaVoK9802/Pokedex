package com.example.pokdex.ui.views


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.pokdex.R
import com.example.pokdex.data.remote.RequestStatus
import com.example.pokdex.data.remote.models.Pokemon
import com.example.pokdex.domain.PokemonDetailViewModel
import com.example.pokdex.ui.theme.AtkColor
import com.example.pokdex.ui.theme.DefColor
import com.example.pokdex.ui.theme.HPColor
import com.example.pokdex.ui.theme.SpAtkColor
import com.example.pokdex.ui.theme.SpDefColor
import com.example.pokdex.ui.theme.SpdColor
import com.example.pokdex.ui.theme.bug
import com.example.pokdex.ui.theme.dark
import com.example.pokdex.ui.theme.dragon
import com.example.pokdex.ui.theme.electric
import com.example.pokdex.ui.theme.fairy
import com.example.pokdex.ui.theme.fighting
import com.example.pokdex.ui.theme.fire
import com.example.pokdex.ui.theme.flying
import com.example.pokdex.ui.theme.ghost
import com.example.pokdex.ui.theme.grass
import com.example.pokdex.ui.theme.ground
import com.example.pokdex.ui.theme.ice
import com.example.pokdex.ui.theme.normal
import com.example.pokdex.ui.theme.poison
import com.example.pokdex.ui.theme.psychic
import com.example.pokdex.ui.theme.rock
import com.example.pokdex.ui.theme.steel
import com.example.pokdex.ui.theme.water
import kotlin.math.round

@Composable
fun PokemonDetailScreen(navController: NavController,pokemonDetailViewModel: PokemonDetailViewModel) {

//    TopBar( navController = navController )

        when (pokemonDetailViewModel.pokeDetailStatus) {
            is RequestStatus.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(100.dp)

                )
            }
            is RequestStatus.Success<Pokemon> -> {

                PokemonStatsContainer(pokemonDetailViewModel,navController)

            }
            is RequestStatus.Error -> Text(text = "Failed to fetch")

        }

    }



@Composable
fun PokemonStatsContainer(pokemonDetailViewModel: PokemonDetailViewModel,navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xfffe0065))) {
     TopBar( navController = navController,pokemonDetailViewModel)
     PokedexImageContainer(pokemonDetailViewModel = pokemonDetailViewModel)
     PokemonDetailContainer(pokemonDetailViewModel =pokemonDetailViewModel)


//        Column {
//
//
//
//            Text(text = pokemonDetailViewModel.pokemonName, color = Color.Black);
//            Text(text = pokemonDetailViewModel.height.toString(), color = Color.Black);
//            Text(text = pokemonDetailViewModel.weight.toString(), color = Color.Black);
//            for(type in pokemonDetailViewModel.types){
//                Text(text = type.type.name, color = Color.Black);
//            }
//            for( stat in pokemonDetailViewModel.statResult) {
//                Text(text = stat.base_stat.toString(), color = Color.Red);
//                Text(text = stat.stat.name, color = Color.Black);
//            }
//
//        }


    }
}


@Composable
fun TopBar( navController: NavController, pokemonDetailViewModel: PokemonDetailViewModel){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(110.dp)
        .background(Color(0xffd30a40))

        , contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxWidth()

                .background(Color(0xffd30a40))
                .padding(15.dp)

        ) {

          NavIcon(navController = navController)


        }
        Row(
            modifier = Modifier
                .fillMaxHeight()

                .align(Alignment.CenterEnd)
                .padding(20.dp)
                .width(260.dp)

        )
        {
            DecorationBalls(ballColor = Color.Red)
            Spacer(modifier = Modifier.width(5.dp))
            DecorationBalls(ballColor = SpdColor)
            Spacer(modifier = Modifier.width(5.dp))
            DecorationBalls(ballColor = HPColor)
            Spacer(modifier = Modifier.width(10.dp))
            PokemonTypeIndicator(pokemonDetailViewModel = pokemonDetailViewModel)
        }
        Spacer(modifier = Modifier
            .height(10.dp)

            .fillMaxWidth()
            .background(Color(0xffd30a40))
            .align(Alignment.BottomCenter)

        )
        Spacer(modifier = Modifier
            .height(5.dp)

            .fillMaxWidth()
            .background(Color.Black)
            .align(Alignment.BottomCenter)

        )
    }
}

@Composable
fun PokemonTypeIndicator(pokemonDetailViewModel: PokemonDetailViewModel){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .border(width = 3.dp, shape = RoundedCornerShape(10.dp), color = Color.Black)
            .background(Color(0xff084035))
            .padding(10.dp),
//                verticalArrangement = Arrangement.Center
    ){

        for(type in pokemonDetailViewModel.types){
            Text(text = type.type.name,
                modifier =
                Modifier
                    .background(typeToColorMapper(type.type.name))
                    .padding(1.dp)
                    .fillMaxWidth()
                ,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun NavIcon(navController: NavController){
    Box(modifier = Modifier
        .height(76.dp)
        .width(76.dp)
        .clip(CircleShape)
        .background(Color.DarkGray)
        ,
        contentAlignment = Alignment.Center) {


        Box(
            modifier = Modifier
                .height(72.dp)
                .width(72.dp)
                .clip(CircleShape)
                .background(color = Color(0xffffffff))
            ,
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .height(67.dp)
                    .width(67.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {



                Box(
                    modifier = Modifier
                        .height(62.dp)
                        .width(62.dp)
                        .clip(CircleShape)
                        .background(color = Color(0xff2e99cc))
                        .padding(11.dp)
                        .clickable { navController.popBackStack() }
                    ,
                    contentAlignment = Alignment.Center

                ) {

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Previous Page",
                        modifier = Modifier
                            .size(35.dp)
                        ,
                        tint = Color.White

                    )
                    Box( modifier = Modifier
                        .height(10.dp)
                        .width(10.dp)
                        .clip(CircleShape)
                        .background(color = Color(0xff86befe))
                        .align(Alignment.TopStart)
                        ,
                    )
                }
            }
        }
    }
}

@Composable
fun DecorationBalls(ballColor:Color){

        Box(
            modifier = Modifier
                .height(29.dp)
                .width(29.dp)
                .clip(CircleShape)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {

                Box(
                    modifier = Modifier
                        .height(25.dp)
                        .width(25.dp)
                        .clip(CircleShape)
                        .background(ballColor)
                        .padding(5.dp)
                    ,
                    contentAlignment = Alignment.Center

                ) {

                    Box( modifier = Modifier
                        .height(6.dp)
                        .width(6.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .align(Alignment.TopStart)
                        ,
                    )
                }


    }
}





@Composable
fun PokedexImageContainer(pokemonDetailViewModel: PokemonDetailViewModel){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(265.dp)
        .background(Color(0xffd30a40))
        .padding(vertical = 15.dp, horizontal = 25.dp)
        ,
        contentAlignment = Alignment.Center
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .border(width = 3.dp, shape = RoundedCornerShape(10.dp), color = Color.Black)
            .padding(10.dp)
            ,

        ){
            Box(modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color(0xff99cc99))
                .border(width = 3.dp, shape = RoundedCornerShape(10.dp), color = Color.DarkGray)
                .padding(bottom = 10.dp)
                ,
                contentAlignment = Alignment.Center
                ){

                SubcomposeAsyncImage(
                    model = pokemonDetailViewModel.imgUrl,
                    contentDescription = "cec",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.TopCenter)
                        .offset(0.dp,-10.dp)
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading ||
                        state is AsyncImagePainter.State.Error) {
                        CircularProgressIndicator(modifier = Modifier.padding(60.dp))
                    }
                    else {
                        SubcomposeAsyncImageContent(modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                        )
                    }
                }
                Text(text = pokemonDetailViewModel.pokemonName,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    fontSize = 20.sp
                )

            }
        }

    }
    Spacer(modifier = Modifier
        .height(5.dp)
        .fillMaxWidth()
        .background(Color.Black)
    )
}

@Composable
fun PokemonDetailContainer(pokemonDetailViewModel: PokemonDetailViewModel){
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color(0xffd30a40))
            .padding(12.dp)
            ,
        contentAlignment = Alignment.Center
    ){
       Column(
         modifier = Modifier
             .fillMaxSize()
             .clip(RoundedCornerShape(10.dp))
             .border(width = 2.dp, shape = RoundedCornerShape(10.dp), color = Color.Black)
             .border(width = 8.dp, shape = RoundedCornerShape(10.dp), color = Color.White)
             .border(width = 10.dp, shape = RoundedCornerShape(10.dp), color = Color.DarkGray)
             .background(color = Color(0xff99cc99))
           ,
           verticalArrangement = Arrangement.SpaceEvenly
       ){
         Row(modifier = Modifier
             .fillMaxWidth()
             .padding(horizontal = 20.dp, vertical = 15.dp)
             .height(70.dp)
             ,
             horizontalArrangement = Arrangement.SpaceEvenly,
             verticalAlignment = Alignment.CenterVertically
         ){
             Column(
                 modifier = Modifier
                     .fillMaxWidth(0.5f)
                     .fillMaxHeight()

                 , horizontalAlignment = Alignment.CenterHorizontally
                 , verticalArrangement = Arrangement.SpaceAround

             ){
                 Icon(
                     painter =
                     painterResource(id = R.drawable.pokemon_height_icon),
                     contentDescription = "height icon",
                     tint = Color.Black
                 )
                 Text(
                     text = (round(pokemonDetailViewModel.height*100f)/1000f).toString()+" m"
                     , color = Color.Black
                 )

             }

             Spacer(modifier = Modifier
                 .fillMaxHeight()
                 .width(3.dp)
                 .background(Color.Black))

             Column(
                 modifier = Modifier
                     .fillMaxWidth()
                     .fillMaxHeight()

                 , horizontalAlignment = Alignment.CenterHorizontally
                 , verticalArrangement = Arrangement.SpaceAround
             ){
                 Icon(
                     painter =
                     painterResource(id = R.drawable.pokemon_weight_icon),
                     contentDescription = "height icon",
                     tint = Color.Black,
                 )
                 Text(text = (round(pokemonDetailViewModel.weight*100f)/1000f).toString()+" Kg"
                     , color = Color.Black
                 )
             }

           }
           Column(modifier = Modifier
               .fillMaxSize()
               .padding(horizontal = 20.dp, vertical = 10.dp)
//               .background(Color.Cyan)
           ){

               StatBar(pokemonDetailViewModel = pokemonDetailViewModel)
           }

       }
    }
}


@Composable
fun StatBar(pokemonDetailViewModel: PokemonDetailViewModel){
    var statTotal:Float = 0f;
    var statCount=0;
    var maxStat = 0;
    for(num in pokemonDetailViewModel.statResult){
        statTotal+=num.base_stat;
        if(maxStat<num.base_stat){
            maxStat = num.base_stat
        }
        statCount+=1;
    }

    for(stat in pokemonDetailViewModel.statResult) {
        val perc:Float = (stat.base_stat.toFloat())/(maxStat)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .border(width = 1.dp, shape = RoundedCornerShape(3.dp), color = Color.Black)
//                .background(Color.Black)
        ) {
            Box(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(perc)
                .clip(shape = RoundedCornerShape(3.dp))
                .background(statToColorMapper(stat.stat.name.lowercase()))
                , contentAlignment = Alignment.Center

            ){
                Text(
                    text = statToShortStatMapping(stat.stat.name.lowercase()),
                    modifier = Modifier.padding(horizontal = 10.dp)
                        .align(Alignment.CenterStart)

                )
                Text(
                    text = stat.base_stat.toString()
                    , modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .align(Alignment.CenterEnd)
                )

            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

fun statToColorMapper(type: String):Color{
     val color = when(type){
         "hp"-> HPColor
         "attack"-> AtkColor
         "defense"-> DefColor
         "special-attack"-> SpAtkColor
         "special-defense"-> SpDefColor
         "speed"-> SpdColor
         else -> Color.Black
     }
    return color
}

fun typeToColorMapper(type:String):Color{
     val color =  when (type) {
         "normal" -> normal
         "fire" -> fire
         "water" -> water
         "electric" -> electric
         "grass" -> grass
         "ice" -> ice
         "fighting" -> fighting
         "poison" -> poison
         "ground" -> ground
         "flying" -> flying
         "psychic" -> psychic
         "bug" -> bug
         "rock" -> rock
         "ghost" -> ghost
         "dragon" -> dragon
         "dark" -> dark
         "steel" -> steel
         "fairy" -> fairy
         else -> Color.Black
     }
    return color
}
fun statToShortStatMapping(stat: String):String{
    val shrotStat = when(stat){
        "hp"->"HP"
        "attack"->"ATK"
        "defense"->"DEF"
        "special-attack"->"S-ATK"
        "special-defense"->"S-DEF"
        "speed"->"SPD"
        else -> ""
    }
    return shrotStat
}

@Preview
@Composable
fun PokedexImageContainerPreview(){
//    PokedexImageContainer(pokemonDetailViewModel = PokemonDetailViewModel())
}

@Preview
@Composable
fun DecorationBallsPreview(){
    DecorationBalls(Color.Red)
}

@Preview
@Composable
fun NavIconPreview(){
    NavIcon(navController = rememberNavController())
}

@Preview
@Composable
fun TopBarPreview(){
//    TopBar( navController = rememberNavController(), pokemonDetailViewModel = PokemonDetailViewModel())
}


@Preview
@Composable
fun PokemonDetailScreenPreview(){
//    PokemonDetailScreen( navController = rememberNavController(),pokemonDetailViewModel = PokemonDetailViewModel())
}