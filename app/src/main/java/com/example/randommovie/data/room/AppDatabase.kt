package com.example.randommovie.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.randommovie.data.room.dao.*
import com.example.randommovie.data.room.entity.*

@Database(
    version = 1,
    entities = [
        MovieDb::class,
        ItemDb::class,
        ItemsForMoviesDb::class,
        UserActionsForMovieDb::class,
        FilterDb::class,
        TokenDb::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    abstract fun itemsDao(): ItemsDao

    abstract fun listDao(): ListDao

    abstract fun filterDao(): FilterDao

    abstract fun tokenDao(): TokenDao
}