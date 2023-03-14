package com.example.randommovie.domain

import com.example.randommovie.domain.entity.Movie

interface ListRepository {

    suspend fun getAllMovies(): List<Movie>
}