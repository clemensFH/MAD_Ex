package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.movieappmad24.R
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import kotlin.jvm.optionals.getOrNull

@Composable
fun DetailScreen(navHostController: NavHostController, movieId: String?){
    //DetailsTopAppBar(navHostController, movieName = "Avatar")
    val movie = getMovies().stream()
        .filter{m -> m.id.equals(movieId)}
        .findFirst().getOrNull()

    if (movie != null) {
        MovieAppMAD24Theme {
            Scaffold(
                topBar = { DetailsTopAppBar(navHostController, movieName = movie.title) }
            ){innerPadding ->
                MovieDetails(movie = movie, modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopAppBar(navHostController: NavHostController, movieName: String){
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.purple_200),
            titleContentColor = colorResource(id = R.color.purple_500),
        ),
        title = {
            Text(movieName)
        },
        navigationIcon = {
            IconButton(onClick = {
                navHostController.popBackStack();
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}

@Composable
fun MovieImageSlides(movie: Movie, modifier: Modifier){
    LazyRow(modifier = modifier) {
        items(movie.images){image ->
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = image,
                    contentScale = ContentScale.Crop,
                    contentDescription = "movie image"
                )
            }
        }
    }
}

@Composable
fun MovieDetails(movie: Movie, modifier: Modifier){
    Column {
        MovieRow(movie = movie)
        MovieImageSlides(modifier = modifier, movie = movie)
    }
}