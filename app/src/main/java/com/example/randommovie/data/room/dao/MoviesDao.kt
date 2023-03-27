package com.example.randommovie.data.room.dao

import androidx.room.*
import com.example.randommovie.data.room.entity.LastMovieDb
import com.example.randommovie.data.room.entity.MovieDb
import com.example.randommovie.data.room.entity.UserActionsForMovieDb
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movieDb: MovieDb)

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserActionsForMovie(userActionsForMovieDb: UserActionsForMovieDb)

    @Query("SELECT movie_id FROM last_movie")
    fun getLastMovieId() : Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLastMovie(lastMovieDb: LastMovieDb)

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun findMovieById(movieId: Long): MovieDb


}