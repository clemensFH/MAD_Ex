package com.example.movieappmad24.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController() // create a NavController instance
    NavHost(navController = navController, // pass the NavController to NavHost
        startDestination = "homescreen") { // pass a start destination

        composable("homescreen"){
            HomeScreen(navController)
        }
        composable("detailscreen/{movieId}") { backStackEntry ->
            DetailScreen(navController, movieId = backStackEntry.arguments?.getString("movieId"))
        }
    }
}