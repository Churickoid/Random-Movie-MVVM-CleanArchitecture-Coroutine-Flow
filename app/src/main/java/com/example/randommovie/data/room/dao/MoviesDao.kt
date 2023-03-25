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
        "WHERE CASE WHEN :type = 0 THEN user_actions_for_movie.in_watchlist = 1 " +
        "WHEN :type = 1 THEN user_actions_for_movie.rating > 0 END " +
        "ORDER BY CASE WHEN :isAsc = 1 THEN user_actions_for_movie.id END ASC, " +
        "CASE WHEN :isAsc = 0 THEN user_actions_for_movie.id END DESC"
    )
    fun getMovieListByFilters(type: Int, isAsc: Boolean): Flow<Map<UserActionsForMovieDb, MovieDb>>

    @Query("SELECT COUNT(*) FROM user_actions_for_movie "+
            "WHERE CASE WHEN :type = 0 THEN user_actions_for_movie.in_watchlist = 1 " +
            "WHEN :type =1 THEN user_actions_for_movie.rating > 0 END")
    fun getMoviesCountByType(type: Int): Flow<Int>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUserActionsForMovie(userActionsForMovieDb: UserActionsForMovieDb)



}