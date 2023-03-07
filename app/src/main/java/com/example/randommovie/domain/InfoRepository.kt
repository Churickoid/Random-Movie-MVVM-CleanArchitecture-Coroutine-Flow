package com.example.randommovie.domain

import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtra

interface InfoRepository {

    suspend fun getMoreInformation(id: Int): MovieExtra
}