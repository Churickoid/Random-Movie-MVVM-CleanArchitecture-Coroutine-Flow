package com.example.randommovie.domain.entity

data class ItemFilter(
    val id: Int,
    val name: String,
    var isActive: Boolean = false
)