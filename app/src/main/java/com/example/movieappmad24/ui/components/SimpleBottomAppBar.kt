package com.example.movieappmad24.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieappmad24.R
import com.example.movieappmad24.navigation.Screen

@Composable
fun MovieAppBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            label = { Text(stringResource(id = R.string.home)) },
            selected = currentRoute == Screen.HomeScreen.route,
            onClick = { navController.navigate(Screen.HomeScreen.route)},
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Go to home"
                )
            }
        )
        NavigationBarItem(
            label = { Text(stringResource(id = R.string.watchlist)) },
            selected = currentRoute == Screen.WatchlistScreen.route,
            onClick = { navController.navigate(Screen.WatchlistScreen.route)},
            icon = {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Go to watchlist"
                )
            }
        )
    }
}