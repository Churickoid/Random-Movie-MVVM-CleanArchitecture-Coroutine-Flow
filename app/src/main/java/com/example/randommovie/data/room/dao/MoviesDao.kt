package com.example.randommovie.data.room.dao

import androidx.room.*
import com.example.randommovie.data.room.entity.MovieDb
import com.example.randommovie.data.room.entity.UserActionsForMovieDb


@Dao
interface MoviesDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movieDb: MovieDb)

    @Query(
        "SELECT * FROM user_actions_for_movie " +
        "JOIN movies ON movies.id = user_actions_for_movie.movie_id"
    )
    suspend fun getAllMoviesWithUserActions(): Map<UserActionsForMovieDb, MovieDb>

    @Upsert
    suspend fun upsertUserActionsForMovie(userActionsForMovieDb: UserActionsForMovieDb)
}