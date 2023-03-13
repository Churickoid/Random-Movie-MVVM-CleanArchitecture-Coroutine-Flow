package com.example.randommovie.domain

import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtra
import com.example.randommovie.domain.entity.SearchFilter


interface MovieRepository{

    suspend fun getRandomMovie(searchFilter: SearchFilter): Movie

    suspend fun getMoreInformation(id: Long): MovieExtra

    fun addMustWatchedMovie(movie: Movie)

    fun addRatedMovie(movie: Movie)

}