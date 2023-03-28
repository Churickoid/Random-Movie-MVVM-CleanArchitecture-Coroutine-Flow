package com.example.randommovie.data

import android.util.Log
import com.example.randommovie.data.retrofit.RetrofitApiInterface
import com.example.randommovie.data.room.dao.ItemsDao
import com.example.randommovie.data.room.dao.ListDao
import com.example.randommovie.data.room.dao.MoviesDao
import com.example.randommovie.data.room.entity.*
import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.ListRepository.Companion.COUNTRY_ITEM_TYPE
import com.example.randommovie.domain.ListRepository.Companion.GENRE_ITEM_TYPE
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.UserInfoAndMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ListRepositoryImpl(
    private val retrofitApiInterface: RetrofitApiInterface,
    private val moviesDao: MoviesDao,
    private val itemsDao: ItemsDao,
    private val listDao: ListDao
) : ListRepository {

    private var isDataExist = false

    override suspend fun getCountriesList(): List<ItemFilter> {
        setGenresAndCountries()
        return itemsDao.getAllItemsByType(COUNTRY_ITEM_TYPE).map { it.toItemFilter() }
    }

    override suspend fun getGenresList(): List<ItemFilter> {
        setGenresAndCountries()
        return  itemsDao.getAllItemsByType(GENRE_ITEM_TYPE).map { it.toItemFilter() }
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
                    itemsDao.getGenresByMovieId(movie.movieId),
                    itemsDao.getCountriesByMovieId(movie.movieId))
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
            itemsDao.insertItemForMovie(
                ItemsForMoviesDb(
                    movie.id, itemsDao.getItemIdByName(it),COUNTRY_ITEM_TYPE
                )
            )
        }
        movie.genre.forEach {
            itemsDao.insertItemForMovie(
                ItemsForMoviesDb(
                    movie.id, itemsDao.getItemIdByName(it),GENRE_ITEM_TYPE
                )
            )
        }

    }

    override suspend fun deleteMovieById(id: Long) {
        moviesDao.deleteMovieById(id)
    }

    private suspend fun setGenresAndCountries() {
        if (!isDataExist) {
            if (itemsDao.getAllItemsByType(GENRE_ITEM_TYPE).isEmpty()) {
                try {
                    val request = retrofitApiInterface.getGenresAndCounties()
                    request.genres.forEach {
                        if (it.genre != "") itemsDao.insertItem(
                            ItemDb(it.id,GENRE_ITEM_TYPE,it.genre)
                        )
                    }
                    request.countries.forEach {
                        if (it.country != "") itemsDao.insertItem(
                            ItemDb(it.id,COUNTRY_ITEM_TYPE ,it.country)
                        )
                    }
                    isDataExist = true
                } catch (e: Exception) {
                    Log.e("!!!!", e.toString())
                }


            } else isDataExist = true
        }
    }

}
