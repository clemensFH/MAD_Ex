package com.example.movieappmad24.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies

@Database(
    entities = [Movie::class], // tables in the db
    version = 1, // schema version; whenever you change schema you have to increase the version number
    exportSchema = false // for schema version history updates
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDAO
    companion object{
        @Volatile
        private var instance: MovieDatabase? = null
        fun getDatabase(context: Context): MovieDatabase{
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        instance = it
                    }
            }
        }
    }

    private fun seedDatabase(context: Context):Callback {
        return object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                getMovies().forEach{ movie: Movie ->

                }
            }
        }
    }
}