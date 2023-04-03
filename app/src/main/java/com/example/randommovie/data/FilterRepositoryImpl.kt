package com.example.randommovie.data

import com.example.randommovie.data.retrofit.RetrofitApiInterface
import com.example.randommovie.data.room.dao.FilterDao
import com.example.randommovie.data.room.dao.ItemsDao
import com.example.randommovie.data.room.entity.FilterDb
import com.example.randommovie.data.room.entity.ItemDb
import com.example.randommovie.domain.FilterRepository
import com.example.randommovie.domain.FilterRepository.Companion.COUNTRY_ITEM_TYPE
import com.example.randommovie.domain.FilterRepository.Companion.GENRE_ITEM_TYPE
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.SearchFilter

class FilterRepositoryImpl(
    private val retrofitApiInterface: RetrofitApiInterface,
    private val itemsDao: ItemsDao,
    private val filterDao: FilterDao
) : FilterRepository {

    var filter: SearchFilter? = null
    override suspend fun setSearchFilter(searchFilter: SearchFilter) {
        filterDao.deleteFilter()
        filterDao.insertFilter(FilterDb.fromSearchFilter(searchFilter, null, null))
        for (item in searchFilter.genres)
            filterDao.insertFilter(FilterDb.fromSearchFilter(searchFilter, item, GENRE_ITEM_TYPE))
        for (item in searchFilter.countries)
            filterDao.insertFilter(FilterDb.fromSearchFilter(searchFilter, item, COUNTRY_ITEM_TYPE))
        filter = searchFilter
    }

    override suspend fun getSearchFilter(): SearchFilter {
        if (filter == null) {
            try {
                val filterDb = filterDao.getFilter()
                val genresId = mutableListOf<Int>()
                val countriesId = mutableListOf<Int>()
                filterDb.forEach {
                    if (it.itemType == GENRE_ITEM_TYPE) genresId += it.itemId!!
                    else if (it.itemType == COUNTRY_ITEM_TYPE) countriesId += it.itemId!!
                }
                filter = filterDb[0].toSearchFilter(genresId, countriesId)
            } catch (e: Exception) {
                filter = SearchFilter()
            }
        }
        return filter!!
    }

    override suspend fun getCountriesList(): List<ItemFilter> {
        setGenresAndCountries(retrofitApiInterface, itemsDao)
        return itemsDao.getAllItemsByType(COUNTRY_ITEM_TYPE)
            .map { it.toItemFilter() }
    }

    override suspend fun getGenresList(): List<ItemFilter> {
        setGenresAndCountries(retrofitApiInterface, itemsDao)
        return itemsDao.getAllItemsByType(GENRE_ITEM_TYPE).map { it.toItemFilter() }
    }

    companion object {
        private var itemsExist = false
        suspend fun setGenresAndCountries(
            retrofitApiInterface: RetrofitApiInterface,
            itemsDao: ItemsDao
        ) {
            if (!itemsExist)
                if (itemsDao.getAllItemsByType(0).isEmpty()) {
                    val request = retrofitApiInterface.getGenresAndCounties()
                    request.genres.forEach {
                        if (it.genre != "") itemsDao.insertItem(
                            ItemDb(it.id, GENRE_ITEM_TYPE, it.genre)
                        )
                    }
                    request.countries.forEach {
                        if (it.country != "") itemsDao.insertItem(
                            ItemDb(it.id, COUNTRY_ITEM_TYPE, it.country)
                        )
                    }
                    itemsExist = true
                }

        }
    }

}