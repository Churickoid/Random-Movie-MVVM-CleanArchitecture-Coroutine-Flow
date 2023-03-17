package com.example.randommovie.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.randommovie.data.room.entity.CountriesForMoviesDb
import com.example.randommovie.data.room.entity.CountryDb
import com.example.randommovie.data.room.entity.MovieDb


@Dao
interface CountriesDao {

    @Query("SELECT * FROM countries ORDER BY country")
    suspend fun getAllCountries() : List<CountryDb>

    @Upsert
    suspend fun addOrReplaceCountry(country :CountryDb)

    @Query("SELECT countries.country FROM countries "
            +"JOIN countries_for_movies "
            +"ON countries.id = countries_for_movies.country_id "
            +"WHERE countries_for_movies.movie_id = :movieId "
    )
    suspend fun getCountriesByMovieId(movieId: Int): List<String>

    @Query("SELECT id FROM countries WHERE country = :name")
    suspend fun getCountryByName(name: String): String

    @Insert
    suspend fun insertCountryForMovie(countriesForMoviesDb: CountriesForMoviesDb)

}