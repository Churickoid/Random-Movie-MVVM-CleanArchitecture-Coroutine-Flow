package com.example.randommovie.data

import android.util.Log
import com.example.randommovie.data.request.requestEntity.Item
import com.example.randommovie.domain.MovieInterface
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtension
import com.example.randommovie.domain.entity.SearchOption
import kotlin.random.Random

class MovieInterfaceImpl(private val retrofitMovieApiInterface: RetrofitMovieApiInterface) :
    MovieInterface {
    override suspend fun getRandomMovie(searchOption: SearchOption): Movie {
        val randYear = Random.nextInt(searchOption.yearBottom, searchOption.yearTop + 1)
        val randRating = Random.nextInt(searchOption.ratingBottom, searchOption.ratingTop)
//        val genre = if (searchOption.genre != null) searchOption.genre!!
//        else Random.nextInt(1, 15)

        val randPage = Random.nextInt(1, 6)
        val movieList = retrofitMovieApiInterface.getMovieList(
            page = randPage,
            yearFrom = randYear,
            yearTo = randYear,
            ratingFrom = randRating,
            ratingTo = randRating+1,
            //genre = genre
        ).items
        val randomItemId = Random.nextInt(movieList.size)
        return itemToMovie(movieList[randomItemId])
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

    private fun itemToMovie(item: Item): Movie {
        return Movie(
            id = item.kinopoiskId,
            titleRu = item.nameRu,
            title = item.nameOriginal,
            poster = item.posterUrlPreview,
            genre = item.genres?.get(0)!!.genre ?: "-",
            releaseDate = item.year,
            ratingKP = item.ratingKinopoisk,
            ratingIMDB = item.ratingImdb,
            country = item.countries?.get(0)!!.country?: "-"
        )
    }

}