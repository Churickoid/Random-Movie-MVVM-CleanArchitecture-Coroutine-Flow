package com.example.randommovie.presentation.screen.filter

import android.util.Log
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
import com.example.randommovie.presentation.tools.Event
import kotlinx.coroutines.launch

class FilterViewModel(private val setSearchFilterUseCase: SetSearchFilterUseCase,
                      private val getGenresUseCase :GetGenresUseCase,
                      private val getCountriesUseCase : GetCountriesUseCase
) : ViewModel() {

    private val searchFilter = SearchFilter()


    private val _genres = MutableLiveData<Event<List<ItemFilter>>>()
    val genres: LiveData<Event<List<ItemFilter>>> = _genres

    private val _countries = MutableLiveData<Event<List<ItemFilter>>>()
    val countries: LiveData<Event<List<ItemFilter>>> = _countries


    private val _countryText = MutableLiveData<String>()
    val countryText: LiveData<String> = _countryText

    private val _genreText = MutableLiveData<String>()
    val genreText: LiveData<String> = _genreText


    fun getGenresList() {
        viewModelScope.launch {
            try {
                _genres.value = Event(getGenresUseCase.invoke())
            }catch (e:Exception){
                Log.e("!!!", e.toString())
            }
        }
    }
    fun getCountryList() {
        viewModelScope.launch {
            try {
                _countries.value = Event(getCountriesUseCase.invoke())
            }catch (e:Exception){
                Log.e("!!!", e.toString())
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
    fun getDefaultFilterValue():SearchFilter{
        return SearchFilter()
    }
    fun listDialogHandler(requestKey: String, list: ArrayList<ItemFilter>) {
        var checkedNames = ""
        val ids = mutableListOf<Int>()
        list.forEach {
            if (it.isActive) {
                checkedNames += "${it.name}, "
                ids += it.id
            }
        }
        checkedNames = checkedNames.dropLast(2)

        when (requestKey){
            FilterFragment.REQUEST_KEY_GENRES -> {
                _genreText.value = checkedNames
                setGenresFilter(ids)
            }
            FilterFragment.REQUEST_KEY_COUNTRIES -> {
                _countryText.value = checkedNames
                setCountryFilter(ids)
            }
            else -> throw Exception("Unknown Dialog Type")
        }
    }
    private fun setGenresFilter(genresIds: List<Int>){
        searchFilter.genres = genresIds
        setSearchFilter(searchFilter)
    }

    private fun setCountryFilter(countriesIds: List<Int>){
        searchFilter.country = countriesIds
        setSearchFilter(searchFilter)
    }


    private fun setSearchFilter(searchFilter: SearchFilter){
        setSearchFilterUseCase.invoke(searchFilter)
    }



}