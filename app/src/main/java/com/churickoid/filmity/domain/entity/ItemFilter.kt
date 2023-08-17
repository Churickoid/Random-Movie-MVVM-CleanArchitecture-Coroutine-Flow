package com.churickoid.filmity.domain.entity


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemFilter(
    val id: Int,
    val name: String,
    var isActive: Boolean = false,
): Parcelable