package com.example.randommovie.domain

import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.SearchFilter

interface FilterRepository {
    fun setSearchFilter(searchFilter: SearchFilter)

    fun getSearchFilter(): SearchFilter

    suspend fun getCountriesList(): List<ItemFilter>

    suspend fun getGenresList(): List<ItemFilter>
}