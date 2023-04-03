package com.example.randommovie.presentation.screen.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.OrderFilter
import com.example.randommovie.domain.entity.SearchFilter
import com.example.randommovie.domain.entity.Type
import com.example.randommovie.domain.usecases.filter.GetCountriesUseCase
import com.example.randommovie.domain.usecases.filter.GetGenresUseCase
import com.example.randommovie.domain.usecases.filter.GetSearchFilterUseCase
import com.example.randommovie.domain.usecases.filter.SetSearchFilterUseCase
import com.example.randommovie.presentation.tools.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class FilterViewModel(
    private val setSearchFilterUseCase: SetSearchFilterUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getSearchFilterUseCase: GetSearchFilterUseCase
) : ViewModel() {

    private var filter = SearchFilter()
        set(value) {
            field = value
            viewModelScope.launch {
                setSearchFilterUseCase(value)
            }
        }


    private val _genres = MutableLiveData<Event<List<ItemFilter>>>()
    val genres: LiveData<Event<List<ItemFilter>>> = _genres

    private val _countries = MutableLiveData<Event<List<ItemFilter>>>()
    val countries: LiveData<Event<List<ItemFilter>>> = _countries


    private val _countryText = MutableLiveData<String>()
    val countryText: LiveData<String> = _countryText

    private val _genreText = MutableLiveData<String>()
    val genreText: LiveData<String> = _genreText

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

   // private val filterFlow: Flow<SearchFilter> =


    fun getDefaultFilter():SearchFilter{
        filter = SearchFilter()
        return filter
    }
    fun getGenresList() =
        errorHandler {
            _genres.value = Event(getGenresUseCase.invoke())
        }


    fun getCountryList() =
        errorHandler {
            _countries.value = Event(getCountriesUseCase.invoke())
        }


    fun setYearFilter(yearBottom: Int, yearTop: Int) {
        filter = filter.copy(yearBottom = yearBottom, yearTop = yearTop)
    }

    fun setRatingFilter(ratingBottom: Int, ratingTop: Int) {
        filter = filter.copy(ratingBottom = ratingBottom, ratingTop = ratingTop)
    }

    fun setOrderFilter(position: Int) {
        val orderFilter = when (position) {
            0 -> OrderFilter.NUM_VOTE
            1 -> OrderFilter.RATING
            2 -> OrderFilter.YEAR
            else -> throw Exception("Invalid position")
        }
        filter = filter.copy(order = orderFilter)
    }

    fun setTypeFilter(position: Int) {
        val type = when (position) {
            0 -> Type.FILM
            1 -> Type.TV_SERIES
            2 -> Type.ALL
            else -> throw Exception("Invalid position")
        }
        filter = filter.copy(type = type)
    }

    fun getFilterValue(): SearchFilter {
        viewModelScope.launch {
            filter = getSearchFilterUseCase()
        }
        return filter
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

        when (requestKey) {
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

    private fun setGenresFilter(genresIds: List<Int>) {
        filter = filter.copy(genres = genresIds)
    }

    private fun setCountryFilter(countriesIds: List<Int>) {
        filter = filter.copy(countries = countriesIds)
    }

    private fun errorHandler(action: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                action()
            } catch (e: UnknownHostException) {
                _error.value = Event("Need internet connection")
            }
        }
    }

}