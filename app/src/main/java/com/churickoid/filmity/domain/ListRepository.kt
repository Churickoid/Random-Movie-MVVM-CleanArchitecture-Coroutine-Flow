package com.churickoid.filmity.domain

import com.churickoid.filmity.domain.entity.Actions
import com.churickoid.filmity.domain.entity.ActionsAndMovie
import kotlinx.coroutines.flow.Flow

interface ListRepository {


    fun getMoviesCountByType(type: Int) : Flow<Int>

    fun getMovieListByFilters(type: Int, order: Int, isAsc: Boolean): Flow<List<ActionsAndMovie>>

    suspend fun addUserInfoForMovie(actionsAndMovie: ActionsAndMovie)

    suspend fun getActionsByMovieId(movieId: Long): Actions?
    suspend fun deleteMovieById(id: Long)

    companion object{
        const val WATCH_LIST_TYPE = 0
        const val RATED_LIST_TYPE = 1

        const val QUEUE_ORDER = 0
        const val ALPHABET_ORDER = 10
        const val RATING_ORDER= 20
        const val YEAR_ORDER = 30
        const val USER_RATING_ORDER= 40




    }
}