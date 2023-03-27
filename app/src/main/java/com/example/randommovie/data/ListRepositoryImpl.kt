package com.example.randommovie.data

import android.util.Log
import com.example.randommovie.data.retrofit.RetrofitApiInterface
import com.example.randommovie.data.room.dao.CountriesDao
import com.example.randommovie.data.room.dao.GenresDao
import com.example.randommovie.data.room.dao.ListDao
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
    private val genresDao: GenresDao,
    private val listDao: ListDao
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

    override fun getMoviesCountByType(type: Int): Flow<Int> {
        return listDao.getMoviesCountByType(type)
    }

    override suspend fun getMovieListByFilters(type: Int, order: Int, isAsc: Boolean): Flow<List<UserInfoAndMovie>> {
        setGenresAndCountries()
        var filter =  when (order) {
            0 -> ListRepository.QUEUE_ORDER
            1 -> ListRepository.ALPHABET_ORDER
            2 -> ListRepository.RATING_ORDER
            3 -> ListRepository.YEAR_ORDER
            4 -> ListRepository.USER_RATING_ORDER
            else -> throw IllegalArgumentException("Unknown id")
        }
        if (isAsc) filter += 1
        return listDao.getMovieListByFilters(type,filter).map { list ->
            list.map {(userAct, movie) ->
                userAct.toUserInfoAndMovie(movie,
                    genresDao.getGenresByMovieId(movie.id),
                    countriesDao.getCountriesByMovieId(movie.id))
            }
        }
    }

    override suspend fun addUserInfoForMovie(userInfoAndMovie: UserInfoAndMovie) {

        setGenresAndCountries()
        val movie = userInfoAndMovie.movie


        moviesDao.insertMovie(MovieDb.fromMovie(movie))

        moviesDao.insertUserActionsForMovie(
            UserActionsForMovieDb.fromUserInfoAndMovie(userInfoAndMovie)
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

    override suspend fun deleteMovieById(id: Long) {
        moviesDao.deleteMovieById(id)
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
                } catch (e: Exception) {
                    Log.e("!!!!", "Need Internet connection to get genres list in local database")
                }


            } else isDataExist = true
        }
    }

}