package com.example.randommovie.presentation.screen.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.SearchFilter
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

    fun changeYearFilter(yearBottom: Int, yearTop: Int) {
        searchFilter.yearBottom = yearBottom
        searchFilter.yearTop = yearTop
        setSearchFilter(searchFilter)
    }
    fun changeRatingFilter(ratingBottom: Int, ratingTop: Int){
        searchFilter.ratingBottom = ratingBottom
        searchFilter.ratingTop = ratingTop
        setSearchFilter(searchFilter)
    }

    fun getSearchFilter():SearchFilter{
        return searchFilter
    }

    private fun setSearchFilter(searchFilter: SearchFilter){
        setSearchFilterUseCase.invoke(searchFilter)
    }


}