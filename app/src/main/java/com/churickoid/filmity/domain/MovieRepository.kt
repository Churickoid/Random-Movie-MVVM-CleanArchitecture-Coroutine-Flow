package com.churickoid.filmity.domain

import com.churickoid.filmity.domain.entity.Movie
import com.churickoid.filmity.domain.entity.MovieExtra
import com.churickoid.filmity.domain.entity.SearchFilter


interface MovieRepository{

    suspend fun getRandomMovie(searchFilter: SearchFilter,tokenKey: String): Movie

    suspend fun getMoreInformation(id: Long,tokenKey: String): MovieExtra

    suspend fun getLastMovie(): Movie?

    suspend fun setLastMovie(movie:Movie)



}