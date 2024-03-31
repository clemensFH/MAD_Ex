package com.example.movieappmad24.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.HomeScreen
import com.example.movieappmad24.screens.WachtlistScreen

@Composable
fun Navigation() {
    val navController = rememberNavController() // create a NavController instance
    NavHost(navController = navController, // pass the NavController to NavHost
        startDestination = Screen.HomeScreen.route) { // pass a start destination

        composable(Screen.HomeScreen.route){
            HomeScreen(navController)
        }

        composable(Screen.WatchlistScreen.route){
            WachtlistScreen(navController)
        }

        composable("${Screen.DetailScreen.route}/{movieId}") { backStackEntry ->
            DetailScreen(navController, movieId = backStackEntry.arguments?.getString("movieId"))
        }
    }
}