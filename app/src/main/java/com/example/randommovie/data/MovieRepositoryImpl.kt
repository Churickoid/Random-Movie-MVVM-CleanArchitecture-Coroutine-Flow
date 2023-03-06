package com.example.randommovie.data

import com.example.randommovie.domain.MovieRepository
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtension
import com.example.randommovie.domain.entity.SearchFilter
import kotlin.random.Random

class MovieRepositoryImpl(private val retrofitApiInterface: RetrofitApiInterface) :
    MovieRepository {


    override suspend fun getRandomMovie(searchFilter: SearchFilter): Movie {
        val randYear = Random.nextInt(searchFilter.yearBottom, searchFilter.yearTop + 1)
        val randRating = Random.nextInt(searchFilter.ratingBottom, searchFilter.ratingTop + 1)

        val genresList = searchFilter.genres
        val genre = if (genresList.isNotEmpty()) genresList[Random.nextInt(genresList.size)]
        else null

        val countryList = searchFilter.country
        val country = if (countryList.isNotEmpty()) countryList[Random.nextInt(countryList.size)]
        else null

        val randPage = Random.nextInt(1, 6)
        var movieList = retrofitApiInterface.getMovieList(
            page = randPage,
            yearFrom = randYear,
            yearTo = randYear,
            ratingFrom = randRating,
            ratingTo = randRating + 1,
            genre = genre,
            country = country,
            order = searchFilter.order.name,
            type = searchFilter.type.name
        ).items

        if (movieList.isEmpty()) {
            movieList = retrofitApiInterface.getMovieList(
                page = 1,
                yearFrom = searchFilter.yearBottom,
                yearTo = searchFilter.yearTop,
                ratingFrom = searchFilter.ratingBottom,
                ratingTo = searchFilter.ratingTop,
                genre = genre,
                country = country,
                order = searchFilter.order.name,
                type = searchFilter.type.name
            ).items
        }

        val randomItemId = Random.nextInt(movieList.size)

        return movieList[randomItemId].toMovie()
    }

    override fun addMustWatchedMovie(movie: Movie) {
        TODO("Not yet implemented")
    }

    override fun addRatedMovie(movie: Movie) {
        TODO("Not yet implemented")
    }


}