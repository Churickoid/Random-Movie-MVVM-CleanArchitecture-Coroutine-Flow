package com.example.randommovie.domain.entity

data class Movie(
    val id : Int,
    val titleRu: String?,
    val title: String?,
    val poster: String?,
    val genre: String?,
    val releaseDate: Int?,
    val ratingKP: Double?,
    val ratingIMDB: Double?,
    val country: String?
)