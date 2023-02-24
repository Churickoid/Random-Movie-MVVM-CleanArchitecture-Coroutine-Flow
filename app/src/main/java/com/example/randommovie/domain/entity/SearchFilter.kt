package com.example.randommovie.domain.entity

data class SearchFilter(
    var yearBottom: Int = 1980,
    var yearTop: Int = 2022,
    var ratingBottom: Int = 5,
    var ratingTop: Int = 9,
    var order:String = RATING,
    val genres: MutableList<Int> = mutableListOf(),
    val country: MutableList<Int> = mutableListOf()

){
    companion object{
        const val YEAR = "YEAR"
        const val NUM_VOTE = "NUM_VOTE"
        const val RATING = "RATING"
    }
}