package com.example.randommovie.data.room.dao

import androidx.room.*
import com.example.randommovie.data.room.entity.CountriesForMoviesDb
import com.example.randommovie.data.room.entity.CountryDb


@Dao
interface CountriesDao {

    @Query("SELECT * FROM countries")
    suspend fun getAllCountries() : List<CountryDb>

    @Insert
    suspend fun insertCountry(country :CountryDb)

    @Query("SELECT countries.country FROM countries "
            +"JOIN countries_for_movies "
            +"ON countries.id = countries_for_movies.country_id "
            +"WHERE countries_for_movies.movie_id = :movieId "
    )
    suspend fun getCountriesByMovieId(movieId: Long): List<String>

    @Query("SELECT id FROM countries WHERE country = :name")
    suspend fun getCountryIdByName(name: String): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountryForMovie(countriesForMoviesDb: CountriesForMoviesDb)

}