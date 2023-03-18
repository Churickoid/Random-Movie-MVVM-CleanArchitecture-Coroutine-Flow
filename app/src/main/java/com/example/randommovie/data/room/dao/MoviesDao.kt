package com.example.randommovie.data.room.dao

import androidx.room.*
import com.example.randommovie.data.room.entity.MovieDb


@Dao
interface MoviesDao {

    @Query("SELECT *  FROM movies ORDER BY title_main")
    suspend fun getAllMovies() : List<MovieDb>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movieDb: MovieDb)
}