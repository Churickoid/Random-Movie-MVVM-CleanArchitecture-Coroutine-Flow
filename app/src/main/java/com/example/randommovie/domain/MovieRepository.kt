package com.example.randommovie.domain

import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtra
import com.example.randommovie.domain.entity.SearchFilter


interface MovieRepository{

    suspend fun getRandomMovie(searchFilter: SearchFilter,tokenKey: String): Movie

    suspend fun getMoreInformation(id: Long,tokenKey: String): MovieExtra

    suspend fun getLastMovie(): Movie?

    suspend fun setLastMovie(movie:Movie)



}