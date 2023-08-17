package com.churickoid.filmity.data.retrofit.movie.entity.list

import com.churickoid.filmity.data.retrofit.movie.entity.Country
import com.churickoid.filmity.data.retrofit.movie.entity.Genre
import com.churickoid.filmity.domain.entity.Movie
import com.churickoid.filmity.domain.entity.Movie.Companion.RATING_NULL

data class Item(
    val countries: List<Country>,
    val genres: List<Genre>,
    val imdbId: String?,
    val kinopoiskId: Long,
    val nameEn: String?,
    val nameOriginal: String?,
    val nameRu: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val ratingImdb: Double?,
    val ratingKinopoisk: Double?,
    val type: String?,
    val year: Int?
){
    fun toMovie(): Movie {
        val country =  this.countries.map{it.country}
        val genre= this.genres.map { it.genre }
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
            year = this.year,
            ratingKP = this.ratingKinopoisk ?: RATING_NULL,
            ratingIMDB = this.ratingImdb?: RATING_NULL

        )
    }
}