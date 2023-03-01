package com.example.randommovie.domain.entity

data class SearchFilter(
    var yearBottom: Int = 1980,
    var yearTop: Int = 2022,
    var ratingBottom: Int = 6,
    var ratingTop: Int = 9,
    var order:Order = Order.RATING,
    var type: Type = Type.FILM,
    var genres: List<Int> = listOf(),
    var country: List<Int> = listOf()

)
enum class Order{
    YEAR,NUM_VOTE,RATING
}

enum class Type{
    FILM,TV_SERIES,ALL
}