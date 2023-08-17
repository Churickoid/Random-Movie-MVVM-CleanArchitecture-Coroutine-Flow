package com.churickoid.filmity.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActionsAndMovie(
    val movie: Movie,
    val userRating: Int = 0,
    val inWatchlist: Boolean = false
):Parcelable