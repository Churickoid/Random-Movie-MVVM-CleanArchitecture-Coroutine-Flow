package com.example.randommovie.domain

import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtension

interface InfoRepository {

    suspend fun getMoreInformation(movie: Movie): MovieExtension
}