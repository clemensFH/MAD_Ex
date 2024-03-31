package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.movieappmad24.ui.components.MovieAppBottomBar
import com.example.movieappmad24.ui.components.MovieAppTopBar
import com.example.movieappmad24.ui.components.MovieList
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme

@Composable
fun HomeScreen(navController: NavHostController) {
    MovieAppMAD24Theme {
        Scaffold(
            topBar = { MovieAppTopBar() },
            bottomBar = { MovieAppBottomBar(navController) }
        ) { innerPadding ->
            MovieList(modifier = Modifier.padding(innerPadding), navController = navController)
        }
    }
}