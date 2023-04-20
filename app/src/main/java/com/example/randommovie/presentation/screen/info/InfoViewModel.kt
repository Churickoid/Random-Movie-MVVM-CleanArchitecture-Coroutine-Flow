package com.example.randommovie.presentation.screen.info

import androidx.lifecycle.*
import com.example.randommovie.domain.entity.ActionsAndMovie
import com.example.randommovie.domain.entity.MovieExtra
import com.example.randommovie.domain.usecases.movie.GetMoreInformationUseCase
import com.example.randommovie.presentation.screen.info.InfoFragment.Companion.ARG_MOVIE
import com.example.randommovie.presentation.tools.Event
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class InfoViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMoreInformationUseCase: GetMoreInformationUseCase
) : ViewModel() {

    private val _movieInfo = MutableLiveData<MovieExtra>()
    val movieInfo: LiveData<MovieExtra> = _movieInfo

    private val _state = MutableLiveData(LOADING_STATE)
    val state: LiveData<Int> = _state

    private val _actionsAndMovie = savedStateHandle.getLiveData<ActionsAndMovie>(ARG_MOVIE)
    val actionsAndMovie: LiveData<ActionsAndMovie> = _actionsAndMovie

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error
    init {
        getMovieInfo(_actionsAndMovie.value!!.movie.id)
    }


    fun getMovieInfo(id: Long) {
        viewModelScope.launch {
            _state.value = LOADING_STATE
            try {
                _movieInfo.value = getMoreInformationUseCase.invoke(id)
                _state.value = VALID_STATE
            }
            catch (e: UnknownHostException) {
                _state.value = ERROR_STATE
                _error.value = Event("Need internet connection")
            }
        }
    }

    fun setNewRating(rating: Int, inWatchlist: Boolean){
        _actionsAndMovie.value = _actionsAndMovie.value!!.copy(userRating = rating, inWatchlist = inWatchlist)
    }

    companion object {
        const val LOADING_STATE = 0
        const val VALID_STATE = 1
        const val ERROR_STATE = 2
    }




}