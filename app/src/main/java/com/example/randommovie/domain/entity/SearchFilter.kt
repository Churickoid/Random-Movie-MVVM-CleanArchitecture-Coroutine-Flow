package com.example.randommovie.domain.entity

class SearchFilter(
    var yearBottom: Int = 1950,
    var yearTop: Int = 2023,
    var ratingBottom: Int = 4,
    var ratingTop: Int = 9,
    val genres: MutableList<Int> = mutableListOf(),
    val country: MutableList<Int> = mutableListOf(),
)