package com.example.movieappmad24.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movieappmad24.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {
    @Insert
    fun insert(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Update
    fun update(movie: Movie)

    @Query("SELECT * FROM movie WHERE dbId = :id")
    fun getById(id: Long): Flow<Movie>

    @Query("SELECT * FROM movie")
    fun getAll(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<Movie>>
}