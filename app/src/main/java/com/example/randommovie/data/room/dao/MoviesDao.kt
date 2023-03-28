package com.example.randommovie.data.room.dao

import androidx.room.*
import com.example.randommovie.data.room.entity.MovieDb
import com.example.randommovie.data.room.entity.UserActionsForMovieDb


@Dao
interface MoviesDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movieDb: MovieDb)

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserActionsForMovie(userActionsForMovieDb: UserActionsForMovieDb)

    @Query("SELECT * FROM movies ORDER BY id DESC LIMIT 1")
    fun getLastMovie() : MovieDb?

    @Query("SELECT EXISTS(SELECT * from user_actions_for_movie WHERE movie_id = :movieId)")
    fun existIdInUserTable(movieId:Long): Boolean

}