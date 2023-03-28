package com.example.randommovie.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.randommovie.data.room.dao.ItemsDao
import com.example.randommovie.data.room.dao.ListDao
import com.example.randommovie.data.room.dao.MoviesDao
import com.example.randommovie.data.room.entity.*

@Database(
    version = 1,
    entities = [
        MovieDb::class,
        ItemDb::class,
        ItemsForMoviesDb::class,
        UserActionsForMovieDb::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    abstract fun itemsDao(): ItemsDao

    abstract fun listDao(): ListDao
}