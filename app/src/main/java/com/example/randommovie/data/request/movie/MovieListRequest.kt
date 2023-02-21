package com.example.randommovie.data.request.movie

data class MovieListRequest(
    val items: List<Item>,
    val total: Int,
    val totalPages: Int
)