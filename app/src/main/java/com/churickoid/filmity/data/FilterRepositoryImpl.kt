package com.churickoid.filmity.data

import com.churickoid.filmity.data.retrofit.movie.MovieApi
import com.churickoid.filmity.data.room.dao.FilterDao
import com.churickoid.filmity.data.room.dao.ItemsDao
import com.churickoid.filmity.data.room.entity.FilterDb
import com.churickoid.filmity.data.room.entity.ItemDb
import com.churickoid.filmity.domain.FilterRepository
import com.churickoid.filmity.domain.FilterRepository.Companion.COUNTRY_ITEM_TYPE
import com.churickoid.filmity.domain.FilterRepository.Companion.GENRE_ITEM_TYPE
import com.churickoid.filmity.domain.entity.ItemFilter
import com.churickoid.filmity.domain.entity.SearchFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilterRepositoryImpl(
    private val movieApi: MovieApi,
    private val itemsDao: ItemsDao,
    private val filterDao: FilterDao
) : FilterRepository {

    var filter: SearchFilter? = null
    override suspend fun setSearchFilter(searchFilter: SearchFilter) {
        withContext(Dispatchers.Default) {
            filterDao.deleteFilter()
        }
        filterDao.insertFilter(FilterDb.fromSearchFilter(searchFilter, null, null))
        for (item in searchFilter.genres)
            filterDao.insertFilter(
                FilterDb.fromSearchFilter(
                    searchFilter,
                    item.id,
                    GENRE_ITEM_TYPE
                )
            )
        for (item in searchFilter.countries)
            filterDao.insertFilter(
                FilterDb.fromSearchFilter(
                    searchFilter,
                    item.id,
                    COUNTRY_ITEM_TYPE
                )
            )
        filter = searchFilter
    }

    override suspend fun getSearchFilter(): SearchFilter {
        if (filter == null) {
            try {
                val request = filterDao.getFilter()
                val genres = mutableListOf<ItemFilter>()
                val countries = mutableListOf<ItemFilter>()
                request.forEach { (filterDb, itemDb) ->
                    if (filterDb.itemType == GENRE_ITEM_TYPE) genres += itemDb.toItemFilter(true)
                    else if (filterDb.itemType == COUNTRY_ITEM_TYPE) countries += itemDb.toItemFilter(
                        true
                    )
                }
                filter = request.keys.first().toSearchFilter(genres, countries)
            } catch (e: Exception) {
                filter = SearchFilter()
            }
        }
        return filter!!
    }

    override suspend fun getCountriesList(tokenKey:String): List<ItemFilter> {
        setGenresAndCountries(movieApi, itemsDao,tokenKey)
        return itemsDao.getAllItemsByType(COUNTRY_ITEM_TYPE)
            .map { it.toItemFilter(filter!!.countries.map { item -> item.id }) }
    }

    override suspend fun getGenresList(tokenKey:String): List<ItemFilter> {
        setGenresAndCountries(movieApi, itemsDao,tokenKey)
        return itemsDao.getAllItemsByType(GENRE_ITEM_TYPE)
            .map { it.toItemFilter(filter!!.genres.map { item -> item.id }) }
    }

    companion object {
        private var itemsExist = false
        suspend fun setGenresAndCountries(
            movieApi: MovieApi,
            itemsDao: ItemsDao,
            tokenKey:String
        ) {
            if (!itemsExist)
                if (itemsDao.getAllItemsByType(0).isEmpty()) {
                    val request = movieApi.getGenresAndCounties(tokenKey)
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