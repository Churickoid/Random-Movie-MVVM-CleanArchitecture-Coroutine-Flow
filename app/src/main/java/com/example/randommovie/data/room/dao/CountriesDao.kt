package com.example.randommovie.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.randommovie.data.room.entity.CountryDb
import com.example.randommovie.data.room.entity.MovieDb


@Dao
interface CountriesDao {

    @Query("SELECT * FROM countries ORDER BY country")
    suspend fun getAllCountries() : List<CountryDb>

    //@Query("SELECT * FROM countries ")
    suspend fun getCountriesByMovieId(movieId: MovieDb): List<CountryDb>

    @Upsert
    suspend fun addOrReplaceCountry(country :CountryDb)
}