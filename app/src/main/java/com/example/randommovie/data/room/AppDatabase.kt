package com.example.randommovie.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.randommovie.data.room.dao.MoviesDao
import com.example.randommovie.data.room.entity.MovieDb

@Database(
    version = 1,
    entities = [
        MovieDb::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao

}