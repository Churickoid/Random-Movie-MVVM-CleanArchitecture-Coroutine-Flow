package com.example.randommovie.data

import android.util.Log
import com.example.randommovie.data.retrofit.RetrofitApiInterface
import com.example.randommovie.data.room.dao.CountriesDao
import com.example.randommovie.data.room.dao.GenresDao
import com.example.randommovie.data.room.dao.MoviesDao
import com.example.randommovie.data.room.entity.*
import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.UserInfoAndMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ListRepositoryImpl(
    private val retrofitApiInterface: RetrofitApiInterface,
    private val moviesDao: MoviesDao,
    private val countriesDao: CountriesDao,
    private val genresDao: GenresDao
) : ListRepository {

    private var isDataExist = false

    override suspend fun getCountriesList(): List<ItemFilter> {
        setGenresAndCountries()
        return countriesDao.getAllCountries().map { it.toItemFilter() }
    }

    override suspend fun getGenresList(): List<ItemFilter> {
        setGenresAndCountries()
        return genresDao.getAllGenres().map { it.toItemFilter() }
    }

    override suspend fun getAllMoviesWithUserActions(): Flow<List<UserInfoAndMovie>> {
        setGenresAndCountries()
        return moviesDao.getAllMoviesWithUserActions().map { list ->
            list.map {
                UserInfoAndMovie(
                    it.key.id,
                    it.value.toMovie(
                        genresDao.getGenresByMovieId(it.value.id),
                        countriesDao.getCountriesByMovieId(it.value.id)
                    ),
                    it.key.rating,
                    it.key.inWatchlist
                )
            }
        }
    }

    override suspend fun addUserInfoForMovie(userInfoAndMovie: UserInfoAndMovie) {

        setGenresAndCountries()
        val movie = userInfoAndMovie.movie


        moviesDao.insertMovie(MovieDb.fromMovie(movie))
        moviesDao.upsertUserActionsForMovie(
            UserActionsForMovieDb(
                userInfoAndMovie.id,
                userInfoAndMovie.movie.id,
                userInfoAndMovie.userRating,
                userInfoAndMovie.inWatchlist
            )
        )
        movie.country.forEach {
            countriesDao.insertCountryForMovie(
                CountriesForMoviesDb(
                    movie.id, countriesDao.getCountryIdByName(it)
                )
            )
        }
        movie.genre.forEach {
            genresDao.insertGenreForMovie(
                GenresForMoviesDb(
                    movie.id, genresDao.getGenreIdByName(it)
                )
            )
        }
    }

    private suspend fun setGenresAndCountries() {
        if (!isDataExist) {
            if (genresDao.getAllGenres().isEmpty() || countriesDao.getAllCountries().isEmpty()) {
                try {
                    val request = retrofitApiInterface.getGenresAndCounties()
                    request.genres.forEach {
                        if (it.genre != "") genresDao.insertGenre(
                            GenreDb(it.id, it.genre)
                        )
                    }
                    request.countries.forEach {
                        if (it.country != "") countriesDao.insertCountry(
                            CountryDb(it.id, it.country)
                        )
                    }
                    isDataExist = true
                }catch (e : Exception){
                    Log.e("!!!!",e.message!!)
                }


            } else isDataExist = true
        }
    }
}