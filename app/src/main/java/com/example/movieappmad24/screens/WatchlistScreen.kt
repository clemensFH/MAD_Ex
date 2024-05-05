package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.components.MovieAppBottomBar
import com.example.movieappmad24.ui.components.MovieAppTopBar
import com.example.movieappmad24.ui.components.MovieList
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.viewmodels.MoviesViewModelFactory

@Composable
fun WachtlistScreen(navController: NavHostController) {
    val db = MovieDatabase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = MoviesViewModelFactory(repository = repository)
    val viewModel: MoviesViewModel = viewModel(factory = factory)

    MovieAppMAD24Theme {
        Scaffold(
            topBar = { MovieAppTopBar() },
            bottomBar = { MovieAppBottomBar(navController) }
        ) { innerPadding ->
            MovieList(movies = viewModel.favMovies,
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                viewModel = viewModel)
        }
    }
}