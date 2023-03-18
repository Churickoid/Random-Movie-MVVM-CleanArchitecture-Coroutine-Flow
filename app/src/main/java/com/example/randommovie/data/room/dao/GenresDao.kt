package com.example.randommovie.data.room.dao

import androidx.room.*
import com.example.randommovie.data.room.entity.GenreDb
import com.example.randommovie.data.room.entity.GenresForMoviesDb

@Dao
interface GenresDao {

    @Query("SELECT * FROM genres")
    suspend fun getAllGenres() : List<GenreDb>

    @Insert
    suspend fun insertGenre(genre :GenreDb)

    @Query("SELECT genres.genre FROM genres "
            +"JOIN genres_for_movies "
            +"ON genres.id = genres_for_movies.genre_id "
            +"WHERE genres_for_movies.movie_id = :movieId "
    )
    suspend fun getGenresByMovieId(movieId: Long): List<String>

    @Query("SELECT id FROM genres WHERE genre = :name")
    suspend fun getGenreIdByName(name: String): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenreForMovie(genresForMoviesDb: GenresForMoviesDb)
}