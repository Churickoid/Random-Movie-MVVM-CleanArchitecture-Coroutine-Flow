package com.example.randommovie.domain.entity

data class UserMovieInfo(
    val id : Int,
    val movie: Movie,
    val userRating: Int,
    val inWatchlist: Boolean
)