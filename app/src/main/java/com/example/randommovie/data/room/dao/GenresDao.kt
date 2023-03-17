package com.example.randommovie.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.randommovie.data.room.entity.CountriesForMoviesDb
import com.example.randommovie.data.room.entity.GenreDb

@Dao
interface GenresDao {

    @Query("SELECT * FROM genres ORDER BY genre")
    suspend fun getAllGenres() : List<GenreDb>

    @Upsert
    suspend fun addOrReplaceGenre(genre :GenreDb)

    @Query("SELECT genres.genre FROM genres "
            +"JOIN genres_for_movies "
            +"ON genres.id = genres_for_movies.genre_id "
            +"WHERE genres_for_movies.movie_id = :movieId "
    )
    suspend fun getGenresByMovieId(movieId: Int): List<String>

    @Query("SELECT id FROM genres WHERE genre = :name")
    suspend fun getGenreByName(name: String): String

    @Insert
    suspend fun insertCountryForMovie(countriesForMoviesDb: CountriesForMoviesDb)
}