package com.churickoid.filmity.data.retrofit.movie.entity.list

data class MovieListRequest(
    val items: List<Item>,
    val total: Int,
    val totalPages: Int
)