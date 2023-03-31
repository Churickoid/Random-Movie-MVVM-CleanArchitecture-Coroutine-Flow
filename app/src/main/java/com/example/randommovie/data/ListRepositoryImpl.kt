package com.example.randommovie.data

import com.example.randommovie.data.room.dao.ItemsDao
import com.example.randommovie.data.room.dao.ListDao
import com.example.randommovie.data.room.dao.MoviesDao
import com.example.randommovie.data.room.entity.ItemsForMoviesDb
import com.example.randommovie.data.room.entity.MovieDb
import com.example.randommovie.data.room.entity.UserActionsForMovieDb
import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.ListRepository.Companion.COUNTRY_ITEM_TYPE
import com.example.randommovie.domain.ListRepository.Companion.GENRE_ITEM_TYPE
import com.example.randommovie.domain.entity.Actions
import com.example.randommovie.domain.entity.ActionsAndMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ListRepositoryImpl(
    private val moviesDao: MoviesDao,
    private val itemsDao: ItemsDao,
    private val listDao: ListDao
) : ListRepository {

    private val defaultDispatcher = Dispatchers.IO
    override fun getMoviesCountByType(type: Int): Flow<Int> {
        return listDao.getMoviesCountByType(type).flowOn(defaultDispatcher)
    }

    override fun getMovieListByFilters(type: Int, order: Int, isAsc: Boolean): Flow<List<ActionsAndMovie>> {
        var filter = order * 10
        if (isAsc) filter += 1
        return listDao.getMovieListByFilters(type, filter).map { list ->
            list.map { (userAct, movie) ->
                userAct.toUserInfoAndMovie(
                    movie,
                    itemsDao.getGenresByMovieId(movie.movieId),
                    itemsDao.getCountriesByMovieId(movie.movieId)
                )
            }
        }.flowOn(defaultDispatcher)
    }

    override suspend fun addUserInfoForMovie(actionsAndMovie: ActionsAndMovie) = withContext(defaultDispatcher){
        val movie = actionsAndMovie.movie

        moviesDao.insertMovie(MovieDb.fromMovie(movie))
        moviesDao.insertUserActionsForMovie(
            UserActionsForMovieDb.fromUserInfoAndMovie(actionsAndMovie)
        )
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

    override suspend fun getActionsByMovieId(movieId: Long): Actions {
        return moviesDao.getUserInfoForMovieById(movieId)?.toActions() ?: Actions()
    }

    override suspend fun deleteMovieById(id: Long) = withContext(defaultDispatcher) {
        moviesDao.deleteMovieById(id)

    }



}
