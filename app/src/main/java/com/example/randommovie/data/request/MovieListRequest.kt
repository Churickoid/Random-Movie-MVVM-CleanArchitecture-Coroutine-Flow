package com.example.randommovie.data.request

import com.example.randommovie.data.request.requestEntity.Item

data class MovieListRequest(
    val items: List<Item>,
    val total: Int,
    val totalPages: Int
)