package com.example.randommovie.data

import android.util.Log
import com.example.randommovie.data.retrofit.RetrofitApiInterface
import com.example.randommovie.data.room.dao.ItemsDao
import com.example.randommovie.data.room.entity.ItemDb
import com.example.randommovie.domain.FilterRepository
import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.SearchFilter
import java.net.ConnectException
import java.net.UnknownHostException

class FilterRepositoryImpl(
    private val retrofitApiInterface: RetrofitApiInterface,
     private val itemsDao: ItemsDao
) : FilterRepository {

    var filter = SearchFilter()
    override fun setSearchFilter(searchFilter: SearchFilter) {
        filter = searchFilter
    }

    override fun getSearchFilter(): SearchFilter {
        return filter
    }

    override suspend fun getCountriesList(): List<ItemFilter> {
        setGenresAndCountries(retrofitApiInterface,itemsDao)
        return itemsDao.getAllItemsByType(ListRepository.COUNTRY_ITEM_TYPE)
            .map { it.toItemFilter() }
    }

    override suspend fun getGenresList(): List<ItemFilter> {
        setGenresAndCountries(retrofitApiInterface,itemsDao)
        return itemsDao.getAllItemsByType(ListRepository.GENRE_ITEM_TYPE).map { it.toItemFilter() }
    }

    companion object{
        private var itemsExist = false
        suspend fun setGenresAndCountries(retrofitApiInterface: RetrofitApiInterface, itemsDao : ItemsDao) {
            if (!itemsExist)
                if (itemsDao.getAllItemsByType(0).isEmpty()){
                val request = retrofitApiInterface.getGenresAndCounties()
                request.genres.forEach {
                    if(it.genre != "")itemsDao.insertItem(
                        ItemDb(it.id, ListRepository.GENRE_ITEM_TYPE, it.genre)
                    )
                }
                request.countries.forEach {
                    if(it.country != "")itemsDao.insertItem(
                        ItemDb(it.id, ListRepository.COUNTRY_ITEM_TYPE, it.country)
                    )
                }
                itemsExist = true
            }

        }
    }

}