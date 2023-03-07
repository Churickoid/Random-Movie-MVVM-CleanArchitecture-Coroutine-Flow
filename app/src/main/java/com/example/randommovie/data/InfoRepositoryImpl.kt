package com.example.randommovie.data

import com.example.randommovie.domain.InfoRepository
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtra

class InfoRepositoryImpl(private val retrofitApiInterface: RetrofitApiInterface) : InfoRepository {

    override suspend fun getMoreInformation(id: Int): MovieExtra {
        val request = retrofitApiInterface.getMovieById(id)
        return MovieExtra(request.coverUrl, request.description,request.filmLength,request.webUrl)
    }

}