package com.example.randommovie.data.request.list

import com.example.randommovie.data.request.Country
import com.example.randommovie.data.request.Genre
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.Movie.Companion.RATING_NULL

data class Item(
    val countries: List<Country>,
    val genres: List<Genre>,
    val imdbId: String?,
    val kinopoiskId: Long,
    val nameEn: String?,
    val nameOriginal: String?,
    val nameRu: String?,
    val posterUrl: String?,
    val posterUrlPreview: String,
    val ratingImdb: Double?,
    val ratingKinopoisk: Double?,
    val type: String?,
    val year: Int?
){
    fun toMovie(): Movie {
        val country: MutableList<String> = mutableListOf()
        val genre: MutableList<String> = mutableListOf()
        this.genres.forEach { genre.add(it.genre)  }
        this.countries.forEach{country.add(it.country)}
        val secondTitle: String
        val firstTitle = if (this.nameRu== null) {
            secondTitle = "—"
            this.nameOriginal!!
        } else {
            secondTitle = this.nameOriginal ?: " — "
            this.nameRu
        }
        return Movie(
            id = this.kinopoiskId,
            titleMain = firstTitle,
            titleSecond = secondTitle,
            posterUrl = this.posterUrlPreview,
            genre = genre,
            country = country,
            releaseDate = this.year,
            ratingKP = this.ratingKinopoisk ?: RATING_NULL,
            ratingIMDB = this.ratingImdb?: RATING_NULL

        )
    }
}