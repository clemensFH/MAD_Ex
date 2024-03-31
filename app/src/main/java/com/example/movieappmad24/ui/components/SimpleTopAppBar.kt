package com.example.movieappmad24.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.movieappmad24.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAppTopBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.purple_200),
            titleContentColor = colorResource(id = R.color.purple_500),
        ),
        title = {
            Text(stringResource(id = R.string.movie_app))
        }
    )
}