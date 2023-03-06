package com.example.randommovie.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id : Int,
    val titleRu: String?,
    val title: String?,
    val posterUrl: String?,
    val genre: List<String>,
    val releaseDate: Int?,
    val ratingKP: Double?,
    val ratingIMDB: Double?,
    val country: List<String>
): Parcelable