package com.churickoid.filmity.domain

import com.churickoid.filmity.domain.entity.ItemFilter
import com.churickoid.filmity.domain.entity.SearchFilter

interface FilterRepository {
    suspend fun setSearchFilter(searchFilter: SearchFilter)

    suspend fun getSearchFilter(): SearchFilter

    suspend fun getCountriesList(tokenKey:String): List<ItemFilter>

    suspend fun getGenresList(tokenKey:String): List<ItemFilter>

    companion object{
        const val GENRE_ITEM_TYPE = 0
        const val COUNTRY_ITEM_TYPE = 1
    }

}