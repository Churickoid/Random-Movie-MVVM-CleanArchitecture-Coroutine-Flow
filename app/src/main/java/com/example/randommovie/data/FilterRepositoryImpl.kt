package com.example.randommovie.data

import com.example.randommovie.data.retrofit.RetrofitApiInterface
import com.example.randommovie.data.room.dao.CountriesDao
import com.example.randommovie.data.room.dao.GenresDao
import com.example.randommovie.data.room.entity.CountryDb
import com.example.randommovie.data.room.entity.GenreDb
import com.example.randommovie.domain.FilterRepository
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.SearchFilter

class FilterRepositoryImpl(
    private val retrofitApiInterface: RetrofitApiInterface,
    private val countriesDao: CountriesDao,
    private val genresDao: GenresDao

) : FilterRepository {

    var filter = SearchFilter()
    override fun setSearchFilter(searchFilter: SearchFilter) {
        filter = searchFilter
    }

    override fun getSearchFilter(): SearchFilter {
        return filter
    }

    override suspend fun getCountriesList(): List<ItemFilter> {
        setGenresAndCountries()
        return countriesDao.getAllCountries().map { it.toItemFilter() }
    }

    override suspend fun getGenresList(): List<ItemFilter> {
        setGenresAndCountries()
        return genresDao.getAllGenres().map { it.toItemFilter() }

    }


    private suspend fun setGenresAndCountries() {
        if (genresDao.getAllGenres().isEmpty() || countriesDao.getAllCountries().isEmpty()) {
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
        }
    }

}