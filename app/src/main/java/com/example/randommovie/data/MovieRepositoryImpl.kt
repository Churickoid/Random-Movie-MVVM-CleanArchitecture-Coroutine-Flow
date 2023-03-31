package com.example.randommovie.data

import android.util.Log
import com.example.randommovie.data.FilterRepositoryImpl.Companion.setGenresAndCountries
import com.example.randommovie.data.retrofit.RetrofitApiInterface
import com.example.randommovie.data.room.dao.ItemsDao
import com.example.randommovie.data.room.dao.MoviesDao
import com.example.randommovie.data.room.entity.ItemsForMoviesDb
import com.example.randommovie.data.room.entity.MovieDb
import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.MovieRepository
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtra
import com.example.randommovie.domain.entity.SearchFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MovieRepositoryImpl(
    private val retrofitApiInterface: RetrofitApiInterface,
    private val moviesDao: MoviesDao,
    private val itemsDao: ItemsDao,
) : MovieRepository {


    override suspend fun getRandomMovie(searchFilter: SearchFilter): Movie {

        setGenresAndCountries(retrofitApiInterface,itemsDao)

        return withContext(Dispatchers.IO) {
            val randYear = Random.nextInt(searchFilter.yearBottom, searchFilter.yearTop + 1)
            val randRating = Random.nextInt(searchFilter.ratingBottom, searchFilter.ratingTop)

            val genresList = searchFilter.genres
            val genre = if (genresList.isNotEmpty()) genresList[Random.nextInt(genresList.size)]
            else null

            val countryList = searchFilter.countries
            val country =
                if (countryList.isNotEmpty()) countryList[Random.nextInt(countryList.size)]
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
                order = searchFilter.orderFilter.name,
                type = searchFilter.type.name
            ).items

            if (movieList.isEmpty()) {
                movieList = retrofitApiInterface.getMovieList(
                    page = 1,
                    yearFrom = randYear,
                    yearTo = randYear,
                    ratingFrom = searchFilter.ratingBottom,
                    ratingTo = searchFilter.ratingTop,
                    genre = genre,
                    country = country,
                    order = searchFilter.orderFilter.name,
                    type = searchFilter.type.name
                ).items
            }

            val randomItemId = Random.nextInt(movieList.size)
            return@withContext movieList[randomItemId].toMovie()
        }
    }

    override suspend fun getMoreInformation(id: Long): MovieExtra {
        val request = retrofitApiInterface.getMovieById(id)

        val isMovie = (request.type != "TV_SERIES")

        return MovieExtra(
            imdbId= request.imdbId,
            headerUrl = request.coverUrl,
            posterUrlHQ = request.posterUrl,
            description = request.description,
            length = request.filmLength,
            webUrl = request.webUrl,
            isMovie = isMovie,
            kinopoiskVoteCount = request.ratingKinopoiskVoteCount,
            imdbVoteCount = request.ratingImdbVoteCount
        )
    }

    override suspend fun getLastMovie(): Movie? = withContext(Dispatchers.IO) {
        val lastMovie = moviesDao.getLastMovie()
        return@withContext lastMovie?.toMovie(
            itemsDao.getGenresByMovieId(lastMovie.movieId),
            itemsDao.getCountriesByMovieId(lastMovie.movieId)
        )

    }

    override suspend fun setLastMovie(movie: Movie) = withContext(Dispatchers.IO) {
        val lastMovieId = moviesDao.getLastMovie()?.movieId ?: -1
        if (!moviesDao.existIdInUserTable(lastMovieId)) moviesDao.deleteMovieById(lastMovieId)
        Log.e("!!!!",moviesDao.existIdInUserTable(lastMovieId).toString())
        moviesDao.insertMovie(MovieDb.fromMovie(movie))
        movie.country.forEach {
            itemsDao.insertItemForMovie(
                ItemsForMoviesDb(
                    movie.id, itemsDao.getItemIdByName(it), ListRepository.COUNTRY_ITEM_TYPE
                )
            )
        }
        movie.genre.forEach {
            itemsDao.insertItemForMovie(
                ItemsForMoviesDb(
                    movie.id, itemsDao.getItemIdByName(it), ListRepository.GENRE_ITEM_TYPE
                )
            )
        }
    }


}