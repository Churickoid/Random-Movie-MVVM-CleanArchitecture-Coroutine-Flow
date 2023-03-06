package com.example.randommovie.data

import com.example.randommovie.domain.InfoRepository
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtension

class InfoRepositoryImpl(private val retrofitApiInterface: RetrofitApiInterface) : InfoRepository {

    override suspend fun getMoreInformation(movie: Movie): MovieExtension {
        val request = retrofitApiInterface.getMovieById(movie.id)
        return MovieExtension(movie,request.coverUrl, request.description,request.filmLength,request.webUrl)
    }

}