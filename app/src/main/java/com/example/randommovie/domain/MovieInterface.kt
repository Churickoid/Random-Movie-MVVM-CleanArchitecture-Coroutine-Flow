package com.example.randommovie.domain

import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtension
import com.example.randommovie.domain.entity.SearchFilter


interface MovieInterface {

    suspend fun getRandomMovie(searchFilter: SearchFilter): Movie

    fun showMoreInformation(movie: Movie): MovieExtension

    fun addMustWatchedMovie(movie: Movie)

    fun addRatedMovie(movie: Movie)

}