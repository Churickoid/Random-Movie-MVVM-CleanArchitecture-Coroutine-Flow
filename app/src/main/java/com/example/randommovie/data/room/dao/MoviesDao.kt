package com.example.randommovie.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.randommovie.data.room.entity.MovieDb


@Dao
interface MoviesDao {

    @Query("SELECT *  FROM movies ORDER BY title_main")
    suspend fun getAllMovies() : List<MovieDb>

    @Insert
    suspend fun addMovie(movieDb: MovieDb)
}