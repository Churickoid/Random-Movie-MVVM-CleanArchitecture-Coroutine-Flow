package com.example.randommovie.data.room.dao

import androidx.room.*
import com.example.randommovie.data.room.entity.MovieDb
import com.example.randommovie.data.room.entity.UserActionsForMovieDb
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movieDb: MovieDb)

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Long)

    @Query(
        "SELECT * FROM user_actions_for_movie " +
        "JOIN movies ON movies.id = user_actions_for_movie.movie_id "+
        "WHERE user_actions_for_movie.in_watchlist = 1")
    fun getWatchlistMovies(): Flow<Map<UserActionsForMovieDb, MovieDb>>

    @Query(
        "SELECT * FROM user_actions_for_movie " +
                "JOIN movies ON movies.id = user_actions_for_movie.movie_id "+
                "WHERE user_actions_for_movie.rating > 0")
    fun getRatedMovies(): Flow<Map<UserActionsForMovieDb, MovieDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUserActionsForMovie(userActionsForMovieDb: UserActionsForMovieDb)

}