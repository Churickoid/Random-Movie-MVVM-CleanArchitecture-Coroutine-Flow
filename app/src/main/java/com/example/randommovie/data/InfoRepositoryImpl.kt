package com.example.randommovie.data

import com.example.randommovie.domain.InfoRepository
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtra

class InfoRepositoryImpl(private val retrofitApiInterface: RetrofitApiInterface) : InfoRepository {

    override suspend fun getMoreInformation(id: Int): MovieExtra {
        val request = retrofitApiInterface.getMovieById(id)

        val type = if (request.type == "TV_SERIES") "series"
        else request.type.lowercase()

        return MovieExtra(
            headerURL = request.coverUrl,
            description = request.description,
            length = request.filmLength,
            webUrl = request.webUrl,
            type = type,
            kinopoiskVoteCount = request.ratingKinopoiskVoteCount,
            imdbVoteCount = request.ratingImdbVoteCount
        )
    }

}