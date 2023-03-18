package com.example.randommovie.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfoAndMovie(
    val id : Long = AUTO_GENERATED,
    val movie: Movie,
    val userRating: Int,
    val inWatchlist: Boolean
):Parcelable{
    companion object{
        const val AUTO_GENERATED: Long = 0
    }
}