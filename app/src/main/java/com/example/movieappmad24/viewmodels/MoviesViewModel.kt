package com.example.movieappmad24.viewmodels

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import kotlin.jvm.optionals.getOrNull

class MoviesViewModel : ViewModel() {
    private val _movies = getMovies().toMutableStateList()

    val movieList: List<Movie>
        get() = _movies

    val favoritesList: List<Movie>
        get() = _movies.filter { m -> m.isFavorite }

    fun toggleMovie(Id: String){
        _movies.find { m -> m.id.equals(Id) }?.toggleFavorite()
    }

    fun getMovieById(Id: String): Movie? {
        val movie = _movies.stream()
            .filter{m -> m.id.equals(Id)}
            .findFirst().getOrNull()

        return movie
    }
}