package com.example.randommovie.data.retrofit.request.list

data class MovieListRequest(
    val items: List<Item>,
    val total: Int,
    val totalPages: Int
)