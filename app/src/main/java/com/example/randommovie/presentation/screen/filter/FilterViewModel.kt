package com.example.randommovie.presentation.screen.filter

import android.util.Log
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
import com.example.randommovie.presentation.screen.filter.FilterFragment.Companion.REQUEST_KEY_COUNTRIES
import com.example.randommovie.presentation.screen.filter.FilterFragment.Companion.REQUEST_KEY_GENRES
import com.example.randommovie.presentation.tools.Event
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class FilterViewModel(
    private val setSearchFilterUseCase: SetSearchFilterUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getSearchFilterUseCase: GetSearchFilterUseCase
) : ViewModel() {


    private var filter: SearchFilter? = null
        set(value) {
            field = value
            if (value != null) saveCurrentFilter(value)
            _startFilter.value = value!!
        }

    private val _startFilter = MutableLiveData<SearchFilter>()
    val startFilter: LiveData<SearchFilter> = _startFilter

    private var genres = listOf<ItemFilter>()
        set(value) {
            val active = value.filter { it.isActive }
            field = value
            _genreText.value = active.joinToString(", ") { it.name }
            filter = filter!!.copy(genres = active.map { it })
        }

    private var countries = listOf<ItemFilter>()
        set(value) {
            val active = value.filter { it.isActive }
            field = value
            _countryText.value = active.joinToString(", ") { it.name }
            filter = filter!!.copy(countries = active.map { it })
        }

    private val _genresEvent = MutableLiveData<Event<List<ItemFilter>>>()
    val genresEvent: LiveData<Event<List<ItemFilter>>> = _genresEvent

    private val _countriesEvent = MutableLiveData<Event<List<ItemFilter>>>()
    val countriesEvent: LiveData<Event<List<ItemFilter>>> = _countriesEvent

    private val _countryText = MutableLiveData<String>()
    val countryText: LiveData<String> = _countryText

    private val _genreText = MutableLiveData<String>()
    val genreText: LiveData<String> = _genreText

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    init {
        setupFilter()
    }

    fun setDefaultFilter() {
        genres.forEach { it.isActive = false }
        Log.e("!!!",genres.toString())
        countries.forEach { it.isActive = false }
        _countryText.value = ""
        _genreText.value = ""
        filter = SearchFilter()
    }

    fun getGenresList() {
        viewModelScope.launch {
            if (genres.isEmpty()) errorHandler {
                genres = getGenresUseCase()
            }
            Log.e("!!!",genres.toString())
            if (genres.isNotEmpty()) _genresEvent.value = Event(genres)
        }
    }

    fun getCountryList() {
        viewModelScope.launch {
            if (countries.isEmpty()) errorHandler {
                countries = getCountriesUseCase()
            }
            if (countries.isNotEmpty()) _countriesEvent.value = Event(countries)
        }
    }


    fun setYearFilter(yearBottom: Int, yearTop: Int) {
        filter = filter!!.copy(yearBottom = yearBottom, yearTop = yearTop)
    }

    fun setRatingFilter(ratingBottom: Int, ratingTop: Int) {
        filter = filter!!.copy(ratingBottom = ratingBottom, ratingTop = ratingTop)
    }

    fun setOrderFilter(position: Int) {
        val orderFilter = when (position) {
            0 -> OrderFilter.RATING
            1 -> OrderFilter.NUM_VOTE
            2 -> OrderFilter.YEAR
            else -> throw Exception("Invalid position")
        }
        filter = filter!!.copy(order = orderFilter)
    }

    fun setTypeFilter(position: Int) {
        val type = when (position) {
            0 -> Type.FILM
            1 -> Type.TV_SERIES
            2 -> Type.ALL
            else -> throw Exception("Invalid position")
        }
        filter = filter!!.copy(type = type)
    }


    fun listDialogHandler(requestKey: String, list: ArrayList<ItemFilter>) {
        when (requestKey) {
            REQUEST_KEY_GENRES -> {
                genres = list
            }
            REQUEST_KEY_COUNTRIES -> {
                countries = list
            }
            else -> throw Exception("Unknown Dialog Type")
        }
    }


    private fun saveCurrentFilter(searchFilter: SearchFilter) {
        viewModelScope.launch {
            setSearchFilterUseCase(searchFilter)
        }
    }

    private fun setupFilter() {
        viewModelScope.launch {
            filter = getSearchFilterUseCase()
            genres.map { ItemFilter(it.id, it.name, false) }
            countries.map { ItemFilter(it.id, it.name, false) }
            _genreText.value = filter!!.genres.joinToString(", ") { it.name }
            _countryText.value = filter!!.countries.joinToString(", ") { it.name }
        }
    }

    private suspend fun errorHandler(action: suspend () -> Unit) {
        try {
            action()
        } catch (e: UnknownHostException) {
            _error.value = Event("Need internet connection")
        }

    }

}