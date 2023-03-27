package com.example.randommovie.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.randommovie.data.room.dao.CountriesDao
import com.example.randommovie.data.room.dao.GenresDao
import com.example.randommovie.data.room.dao.ListDao
import com.example.randommovie.data.room.dao.MoviesDao
import com.example.randommovie.data.room.entity.*

@Database(
    version = 1,
    entities = [
        MovieDb::class,
        GenreDb::class,
        CountryDb::class,
        GenresForMoviesDb::class,
        CountriesForMoviesDb::class,
        UserActionsForMovieDb::class,
        LastMovieDb::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    abstract fun genresDao(): GenresDao

    abstract fun countriesDao(): CountriesDao

    abstract fun listDao(): ListDao
}