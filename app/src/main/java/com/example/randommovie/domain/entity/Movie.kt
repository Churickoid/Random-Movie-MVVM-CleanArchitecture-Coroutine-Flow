package com.example.randommovie.domain.entity

data class Movie(
    val id : Int,
    val title: String,
    val poster: String,
    val genre: String,
    val releaseDate: String,
    val rating: Double,
    val country: String
)