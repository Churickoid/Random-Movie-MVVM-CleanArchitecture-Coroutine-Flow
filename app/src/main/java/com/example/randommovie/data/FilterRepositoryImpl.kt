package com.example.randommovie.data

import com.example.randommovie.data.retrofit.RetrofitApiInterface
import com.example.randommovie.data.room.dao.CountriesDao
import com.example.randommovie.data.room.dao.GenresDao
import com.example.randommovie.data.room.entity.CountryDb
import com.example.randommovie.data.room.entity.GenreDb
import com.example.randommovie.domain.FilterRepository
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.SearchFilter

class FilterRepositoryImpl : FilterRepository {

    var filter = SearchFilter()
    override fun setSearchFilter(searchFilter: SearchFilter) {
        filter = searchFilter
    }

    override fun getSearchFilter(): SearchFilter {
        return filter
    }



}