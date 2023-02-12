package com.example.randommovie.domain.entity

class SearchOption(
    var yearBottom: Int = 1950,
    var yearTop: Int = 2023,
    var ratingBottom: Int = 4,
    var ratingTop: Int = 9,
    var genre: Int? = null
)