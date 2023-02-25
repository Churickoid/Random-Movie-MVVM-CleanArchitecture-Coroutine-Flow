package com.example.randommovie.presentation.screen.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.Order
import com.example.randommovie.domain.entity.SearchFilter
import com.example.randommovie.domain.entity.Type
import com.example.randommovie.domain.usecases.filter.GetCountriesUseCase
import com.example.randommovie.domain.usecases.filter.GetGenresUseCase
import com.example.randommovie.domain.usecases.filter.SetSearchFilterUseCase
import kotlinx.coroutines.launch

class FilterViewModel(private val setSearchFilterUseCase: SetSearchFilterUseCase,
                      private val getGenresUseCase :GetGenresUseCase,
                      private val getCountriesUseCase : GetCountriesUseCase
) : ViewModel() {

    private val searchFilter = SearchFilter()

    private val _genres = MutableLiveData<List<ItemFilter>>()
    val genres: LiveData<List<ItemFilter>> = _genres

    private val _countries = MutableLiveData<List<ItemFilter>>()
    val countries: LiveData<List<ItemFilter>> = _countries


    fun getGenresList() {
        viewModelScope.launch {
            try {
                _genres.value = getGenresUseCase.invoke()
            }catch (e:Exception){

            }
        }
    }
    fun getCountryList() {
        viewModelScope.launch {
            try {
                _countries.value = getCountriesUseCase.invoke()
            }catch (e:Exception){

            }
        }
    }

    fun setYearFilter(yearBottom: Int, yearTop: Int) {
        searchFilter.yearBottom = yearBottom
        searchFilter.yearTop = yearTop
        setSearchFilter(searchFilter)
    }
    fun setRatingFilter(ratingBottom: Int, ratingTop: Int){
        searchFilter.ratingBottom = ratingBottom
        searchFilter.ratingTop = ratingTop
        setSearchFilter(searchFilter)
    }

    fun setOrderFilter(position: Int){
        val order = when(position){
            0 -> Order.RATING
            1 -> Order.NUM_VOTE
            2 -> Order.YEAR
            else -> throw Exception("Invalid position")
        }
        searchFilter.order = order
        setSearchFilter(searchFilter)
    }
    fun setTypeFilter(position: Int){
        val type = when(position){
            0 -> Type.FILM
            1 -> Type.TV_SERIES
            2 -> Type.ALL
            else -> throw Exception("Invalid position")
        }
        searchFilter.type = type
        setSearchFilter(searchFilter)
    }
    fun setGenresFilter(genresIds: List<Int>){
        searchFilter.genres = genresIds
        setSearchFilter(searchFilter)
    }

    fun setCountryFilter(countriesIds: List<Int>){
        searchFilter.country = countriesIds
        setSearchFilter(searchFilter)
    }

    fun getDefaultFilterValue():SearchFilter{
        return SearchFilter()
    }

    private fun setSearchFilter(searchFilter: SearchFilter){
        setSearchFilterUseCase.invoke(searchFilter)
    }


}