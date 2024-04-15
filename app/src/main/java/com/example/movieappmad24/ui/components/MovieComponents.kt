package com.example.movieappmad24.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.movieappmad24.R
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.navigation.Screen
import com.example.movieappmad24.viewmodels.MoviesViewModel

@Composable
fun MovieList(modifier: Modifier,
              navController: NavHostController,
              movies: List<Movie> ,
              viewModel: MoviesViewModel) {
    LazyColumn(modifier = modifier) {
        items(movies) { movie ->
            MovieRow(
                movie = movie,
                onItemClick = {movieId ->
                    navController.navigate(route = "${Screen.DetailScreen.route}/$movieId")
                },
                onToggleFavorite = {movieId ->
                    viewModel.toggleMovie(movieId)
                }
            )
        }
    }
}

@Composable
fun MovieRow(movie: Movie,
             onItemClick: (String) -> Unit = {},
             onToggleFavorite: (String) -> Unit = {}) {
    var showDetails by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                onItemClick(movie.id)
            },
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(10.dp),
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = movie.images[0],
                    contentScale = ContentScale.Crop,
                    contentDescription = "movie image"
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        modifier = Modifier
                            .clickable { onToggleFavorite(movie.id) },
                        tint = Color.Red,
                        imageVector =
                        if (movie.isFavorite) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        contentDescription = "Add to favorites"
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = movie.title)
                Icon(
                    modifier = Modifier
                        .clickable {
                            showDetails = !showDetails
                        },
                    imageVector =
                    if (showDetails) Icons.Filled.KeyboardArrowDown
                    else Icons.Default.KeyboardArrowUp, contentDescription = "show more"
                )
            }

            AnimatedVisibility(showDetails) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                ) {
                    Text(text = "Director: ${movie.director}")
                    Text(text = "Released: ${movie.year}")
                    Text(text = "Genre: ${movie.genre}")
                    Text(text = "Actors: ${movie.actors}")
                    Text(text = "Rating: ${movie.rating}")
                    Divider(modifier = Modifier.fillMaxWidth())
                    Text(text = "Plot: ${movie.plot}")
                }
            }
        }
    }
}

@Composable
fun MovieImageSlides(movie: Movie) {
    LazyRow {
        items(movie.images) { image ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                elevation = CardDefaults.cardElevation(10.dp),
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
fun MovieTrailer(movie: Movie) {
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    var context = LocalContext.current
    val mediaItem = MediaItem.fromUri("android.resource://${context.packageName}"
            + "/${movie.trailer}")

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            exoPlayer.release()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column{
        Text(stringResource(id = R.string.movie_trailer))
        Spacer(Modifier.height(2.dp))
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            factory = {
                PlayerView(context).also { playerView ->
                    playerView.player = exoPlayer
                }
            },
            update = {
                when (lifecycle) {
                    Lifecycle.Event.ON_RESUME -> {
                        it.onPause()
                        it.player?.pause()
                    }

                    Lifecycle.Event.ON_STOP -> {
                        it.onResume()
                    }

                    else -> Unit
                }
            }
        )
    }
}

@Composable
fun MovieDetails(movie: Movie, modifier: Modifier,
                 viewModel: MoviesViewModel) {
    Column(modifier = modifier) {
        MovieRow(movie = movie,
            onToggleFavorite = {movieId ->
                viewModel.toggleMovie(movieId)
            }
        )
        Spacer(Modifier.height(5.dp))
        MovieTrailer(movie = movie)
        Spacer(Modifier.height(5.dp))
        MovieImageSlides(movie = movie)
    }
}