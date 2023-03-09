package com.example.randommovie.domain.entity

data class MovieExtra (
    val headerURL: String?,
    val description: String?,
    val length: Int?,
    val webUrl: String,
    val type: String,
    val kinopoiskVoteCount: Int?,
    val imdbVoteCount: Int?
)