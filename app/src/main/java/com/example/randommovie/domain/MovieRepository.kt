package com.example.randommovie.domain

import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtension
import com.example.randommovie.domain.entity.SearchFilter


interface MovieRepository{

    var searchFilter : SearchFilter
    suspend fun getRandomMovie(): Movie

    fun showMoreInformation(movie: Movie): MovieExtension

    fun addMustWatchedMovie(movie: Movie)

    fun addRatedMovie(movie: Movie)

}