package com.example.randommovie.domain.entity

class SearchFilter(
    var yearBottom: Int = 1980,
    var yearTop: Int = 2022,
    var ratingBottom: Int = 5,
    var ratingTop: Int = 9,
    val genres: MutableList<Int> = mutableListOf(),
    val country: MutableList<Int> = mutableListOf(),
)