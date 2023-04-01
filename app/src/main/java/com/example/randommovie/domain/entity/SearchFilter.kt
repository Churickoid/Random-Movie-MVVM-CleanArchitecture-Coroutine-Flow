package com.example.randommovie.domain.entity

data class SearchFilter(
    val yearBottom: Int = 1980,
    val yearTop: Int = 2022,
    val ratingBottom: Int = 6,
    val ratingTop: Int = 9,
    val order:OrderFilter = OrderFilter.RATING,
    val type: Type = Type.FILM,
    val genres: List<Int> = listOf(),
    val countries: List<Int> = listOf()

)
enum class OrderFilter{
    YEAR,NUM_VOTE,RATING
}

enum class Type{
    FILM,TV_SERIES,ALL
}