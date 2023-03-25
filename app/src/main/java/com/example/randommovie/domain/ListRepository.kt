package com.example.randommovie.domain

import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.UserInfoAndMovie
import kotlinx.coroutines.flow.Flow

interface ListRepository {

    suspend fun getCountriesList(): List<ItemFilter>

    suspend fun getGenresList(): List<ItemFilter>


    fun getMoviesCountByType(type: Int) : Flow<Int>

    suspend fun getMovieListByFilters(type: Int,order: Int, isAsc: Boolean): Flow<List<UserInfoAndMovie>>

    suspend fun addUserInfoForMovie(userInfoAndMovie: UserInfoAndMovie)

    suspend fun deleteMovieById(id: Long)

    companion object{
        const val WATCHLIST_TYPE = 0
        const val RATED_TYPE = 1

        const val QUEUE_ORDER = 0
        const val ALPHABET_ORDER = 1
        const val RATING_ORDER = 2
        const val USER_RATING_ORDER = 3

    }
}