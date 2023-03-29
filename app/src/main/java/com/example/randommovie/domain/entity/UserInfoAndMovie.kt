package com.example.randommovie.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfoAndMovie(
    val id : Long = AUTO_GENERATED,
    val movie: Movie,
    val userRating: Int = 0,
    val inWatchlist: Boolean = false
):Parcelable{
    companion object{
        const val AUTO_GENERATED: Long = 0
    }
}