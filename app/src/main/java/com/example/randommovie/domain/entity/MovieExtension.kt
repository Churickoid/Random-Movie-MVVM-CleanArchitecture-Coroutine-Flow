package com.example.randommovie.domain.entity

data class MovieExtension (
    val movie: Movie,
    val headerURL: String?,
    val description: String?,
    val length: Int?,
    val webUrl: String
)