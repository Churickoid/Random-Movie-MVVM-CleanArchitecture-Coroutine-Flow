package com.churickoid.filmity.data

import com.churickoid.filmity.data.FilterRepositoryImpl.Companion.setGenresAndCountries
import com.churickoid.filmity.data.retrofit.movie.MovieApi
import com.churickoid.filmity.data.room.dao.ItemsDao
import com.churickoid.filmity.data.room.dao.MoviesDao
import com.churickoid.filmity.data.room.entity.ItemsForMoviesDb
import com.churickoid.filmity.data.room.entity.MovieDb
import com.churickoid.filmity.domain.FilterRepository.Companion.COUNTRY_ITEM_TYPE
import com.churickoid.filmity.domain.FilterRepository.Companion.GENRE_ITEM_TYPE
import com.churickoid.filmity.domain.MovieRepository
import com.churickoid.filmity.domain.entity.Movie
import com.churickoid.filmity.domain.entity.MovieExtra
import com.churickoid.filmity.domain.entity.SearchFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val moviesDao: MoviesDao,
    private val itemsDao: ItemsDao,
) : MovieRepository {


    override suspend fun getRandomMovie(searchFilter: SearchFilter,tokenKey: String): Movie {

        setGenresAndCountries(movieApi,itemsDao,tokenKey)

        return withContext(Dispatchers.IO) {
            val randYear = Random.nextInt(searchFilter.yearBottom, searchFilter.yearTop + 1)

            val genresList = searchFilter.genres
            val genre = if (genresList.isNotEmpty()) genresList[Random.nextInt(genresList.size)]
            else null

            val countryList = searchFilter.countries
            val country =
                if (countryList.isNotEmpty()) countryList[Random.nextInt(countryList.size)]
                else null

            val randPage = Random.nextInt(1, 6)
            var movieList = movieApi.getMovieList( tokenKey,
                page = randPage,
                yearFrom = randYear,
                yearTo = randYear,
                ratingFrom = searchFilter.ratingBottom,
                ratingTo = searchFilter.ratingTop,
                genre = genre?.id,
                country = country?.id,
                order = searchFilter.order.name,
                type = searchFilter.type.name
            ).items


            if (movieList.isEmpty()) {
                movieList = movieApi.getMovieList(
                    tokenKey,
                    page = 1,
                    yearFrom = randYear,
                    yearTo = randYear,
                    ratingFrom = searchFilter.ratingBottom,
                    ratingTo = searchFilter.ratingTop,
                    genre = genre?.id,
                    country = country?.id,
                    order = searchFilter.order.name,
                    type = searchFilter.type.name
                ).items
            }

            val randomItemId = Random.nextInt(movieList.size)
            return@withContext movieList[randomItemId].toMovie()
        }
    }

    override suspend fun getMoreInformation(id: Long,tokenKey: String): MovieExtra {
        val request = movieApi.getMovieById(tokenKey
            ,id)

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
        moviesDao.insertMovie(MovieDb.fromMovie(movie))
        movie.country.forEach {
            itemsDao.insertItemForMovie(
                ItemsForMoviesDb(
                    movie.id, itemsDao.getItemIdByName(it), COUNTRY_ITEM_TYPE
                )
            )
        }
        movie.genre.forEach {
            itemsDao.insertItemForMovie(
                ItemsForMoviesDb(
                    movie.id, itemsDao.getItemIdByName(it), GENRE_ITEM_TYPE
                )
            )
        }
    }


}