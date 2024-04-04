package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import com.example.movieappmad24.R
import com.example.movieappmad24.models.getMovieById
import com.example.movieappmad24.ui.components.MovieDetails
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.viewmodels.MoviesViewModel

@Composable
fun DetailScreen(
    navHostController: NavHostController,
    movieViewModel: MoviesViewModel,
    movieId: String?
) {
    if (movieId == null) return

    val movie = movieViewModel.getMovieById(movieId)

    if (movie != null) {
        MovieAppMAD24Theme {
            Scaffold(
                topBar = { DetailsTopAppBar(navHostController, movieName = movie.title) }
            ) { innerPadding ->
                MovieDetails(movie = movie,
                            modifier = Modifier.padding(innerPadding),
                            viewModel = movieViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopAppBar(navHostController: NavHostController, movieName: String) {
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