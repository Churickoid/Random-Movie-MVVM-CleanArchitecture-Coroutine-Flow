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
    val country: List<String>,
    val year: Int?,
    val ratingKP: Double,
    val ratingIMDB: Double

): Parcelable{
    companion object{
        const val RATING_NULL = 0.0
    }
}