package com.example.randommovie.data

import com.example.randommovie.domain.FilterRepository
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