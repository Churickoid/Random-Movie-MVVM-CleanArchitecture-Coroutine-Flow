package com.example.randommovie.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.randommovie.data.room.dao.CountriesDao
import com.example.randommovie.data.room.dao.GenresDao
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
        UserActionsForMovieDb::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao

    abstract fun getGenresDao(): GenresDao

    abstract fun getCountriesDao(): CountriesDao

}