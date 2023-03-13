package com.example.randommovie.domain.entity

data class MovieExtra (
    val headerUrl: String?,
    val posterUrlHQ: String,
    val description: String?,
    val length: Int?,
    val webUrl: String,
    val isMovie: Boolean,
    val kinopoiskVoteCount: Int?,
    val imdbVoteCount: Int?
)