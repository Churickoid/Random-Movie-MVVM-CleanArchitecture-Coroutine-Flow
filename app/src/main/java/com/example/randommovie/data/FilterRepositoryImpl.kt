package com.example.randommovie.data

import com.example.randommovie.domain.FilterRepository
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.SearchFilter

class FilterRepositoryImpl(private val retrofitApiInterface: RetrofitApiInterface): FilterRepository {


    var countriesList = listOf<ItemFilter>()
    var genresList = listOf<ItemFilter>()

    override fun setSearchFilter(searchFilter: SearchFilter) {
        TODO("Not yet implemented")
    }

    override suspend fun getCountriesList(): List<ItemFilter> {
        if (countriesList.isEmpty()) setGenresAndCountries()
        return countriesList
    }

    override suspend fun getGenresList(): List<ItemFilter> {
        if (genresList.isEmpty()) setGenresAndCountries()
        return genresList
    }

    private suspend fun setGenresAndCountries(){
        val request = retrofitApiInterface.getGenresAndCounties()
        val mutableGenres = mutableListOf<ItemFilter>()
        val mutableCountries = mutableListOf<ItemFilter>()
        request.genres.forEach { mutableGenres.add(it.toItemFilter()) }
        request.countries.forEach { mutableCountries.add(it.toItemFilter()) }
        genresList = mutableGenres
        countriesList = mutableCountries
    }

}