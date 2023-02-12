package com.example.randommovie.domain

import com.example.randommovie.data.request.requestEntity.Item
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtension
import com.example.randommovie.domain.entity.SearchOption


interface MovieInterface {

    suspend fun getRandomMovie(searchOption: SearchOption): Movie

    fun showMoreInformation(movie: Movie): MovieExtension

    fun addMustWatchedMovie(movie: Movie)

    fun addRatedMovie(movie: Movie)

}