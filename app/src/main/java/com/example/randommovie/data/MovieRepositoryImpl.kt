package com.example.randommovie.data

import android.util.Log
import com.example.randommovie.data.request.movie.Item
import com.example.randommovie.domain.MovieRepository
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtension
import com.example.randommovie.domain.entity.SearchFilter
import kotlin.random.Random

class MovieRepositoryImpl(private val retrofitApiInterface: RetrofitApiInterface) :
    MovieRepository {



    override suspend fun getRandomMovie(searchFilter : SearchFilter): Movie {
        val randYear = Random.nextInt(searchFilter.yearBottom, searchFilter.yearTop + 1)
        val randRating = Random.nextInt(searchFilter.ratingBottom, searchFilter.ratingTop)

        val genresList = searchFilter.genres
        val genre = if (genresList.isNotEmpty()) genresList[Random.nextInt(genresList.size)]
        else null

        val randPage = Random.nextInt(1, 6)
        val movieList = retrofitApiInterface.getMovieList(
            page = randPage,
            yearFrom = randYear,
            yearTo = randYear,
            ratingFrom = randRating,
            ratingTo = randRating + 1,
            genre = genre
        ).items
        val randomItemId = Random.nextInt(movieList.size)

        return movieList[randomItemId].toMovie()
    }

       override fun showMoreInformation(movie: Movie): MovieExtension {
        TODO("Not yet implemented")
    }

    override fun addMustWatchedMovie(movie: Movie) {
        TODO("Not yet implemented")
    }

    override fun addRatedMovie(movie: Movie) {
        TODO("Not yet implemented")
    }


}