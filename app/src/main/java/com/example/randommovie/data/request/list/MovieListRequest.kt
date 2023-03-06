package com.example.randommovie.data.request.list

data class MovieListRequest(
    val items: List<Item>,
    val total: Int,
    val totalPages: Int
)