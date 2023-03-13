package com.example.randommovie.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id : Long,
    val titleMain: String,
    val titleSecond: String,
    val posterUrl: String,
    val genre: List<String>,
    val releaseDate: Int?,
    val ratingKP: Double,
    val ratingIMDB: Double,
    val country: List<String>
): Parcelable{
    companion object{
        const val RATING_NULL = 0.0
    }
}