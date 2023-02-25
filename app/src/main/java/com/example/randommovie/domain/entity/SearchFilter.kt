package com.example.randommovie.domain.entity

data class SearchFilter(
    var yearBottom: Int = 1980,
    var yearTop: Int = 2022,
    var ratingBottom: Int = 5,
    var ratingTop: Int = 9,
    var order:Order = Order.RATING,
    var type: Type = Type.FILM,
    val genres: MutableList<Int> = mutableListOf(),
    val country: MutableList<Int> = mutableListOf()

)
enum class Order{
    YEAR,NUM_VOTE,RATING
}

enum class Type{
    FILM,TV_SERIES,ALL
}