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
        "ORDER BY CASE WHEN :filter = 0 THEN user_actions_for_movie.id END DESC, " +
        "CASE WHEN :filter = 1 THEN user_actions_for_movie.id END ASC, " +
        "CASE WHEN :filter = 10 THEN movies.title_main END DESC, " +
        "CASE WHEN :filter = 11 THEN movies.title_main END ASC, " +
        "CASE WHEN :filter = 20 THEN movies.rating_kp END DESC, "  +
        "CASE WHEN :filter = 21 THEN movies.rating_kp END ASC, " +
        "CASE WHEN :filter = 30 THEN movies.year END DESC, "  +
        "CASE WHEN :filter = 31 THEN movies.year END ASC, " +
        "CASE WHEN :filter = 40 THEN user_actions_for_movie.rating END DESC, " +
        "CASE WHEN :filter = 41 THEN user_actions_for_movie.rating END ASC "
    )
    fun getMovieListByFilters(type: Int, filter:Int): Flow<Map<UserActionsForMovieDb, MovieDb>>

    @Query("SELECT COUNT(*) FROM user_actions_for_movie "+
            "WHERE CASE WHEN :type = 0 THEN user_actions_for_movie.in_watchlist = 1 " +
            "WHEN :type =1 THEN user_actions_for_movie.rating > 0 END")
    fun getMoviesCountByType(type: Int): Flow<Int>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUserActionsForMovie(userActionsForMovieDb: UserActionsForMovieDb)



}