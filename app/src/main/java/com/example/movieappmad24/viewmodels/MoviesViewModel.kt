package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movies = MutableStateFlow(listOf<Movie>())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    init {
        viewModelScope.launch {
            //addMovie(getMovies()[0])

            repository.getAllMovies().distinctUntilChanged().collect { listOfMovies ->
                    _movies.value = listOfMovies
                }
        }
    }

    suspend fun addMovie(movie: Movie){
        repository.addMovie(movie)
    }

    suspend fun getMovie(movieId: String): Flow<Movie?> {
        return repository.getMovieById(1)
    }

    suspend fun getMovieById(movieId: String): Movie {
        return movies.value.first { m -> m.id == movieId }
    }
//
//    val movieList: Flow<List<Movie>>
//        get() = movies
//
//    val favoritesList: Flow<List<Movie>>
//        get() = repository.getFavoriteMovies()

//    suspend fun toggleMovie(Id: Long){
//        val toUpdate = _movies.firstOrNull { m ->  }
//        repository.updateMovie(toUpdate)
//    }
//
//    fun getMovieById(Id: Long): Movie {
//        repository.getMovieById(Id)
//    }
}