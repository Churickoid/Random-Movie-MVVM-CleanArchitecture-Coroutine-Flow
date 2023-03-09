package com.example.randommovie.data

import com.example.randommovie.domain.InfoRepository
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtra

class InfoRepositoryImpl(private val retrofitApiInterface: RetrofitApiInterface) : InfoRepository {

    override suspend fun getMoreInformation(id: Int): MovieExtra {
        val request = retrofitApiInterface.getMovieById(id)
        return MovieExtra(
            headerURL = request.coverUrl,
            description = request.description,
            length = request.filmLength,
            webUrl = request.webUrl,
            type = request.type,
            kinopoiskVoteCount = request.ratingKinopoiskVoteCount,
            imdbVoteCount = request.ratingImdbVoteCount
        )
    }

}