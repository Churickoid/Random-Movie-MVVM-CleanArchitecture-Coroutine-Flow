package com.churickoid.filmity.presentation.screen.info

import androidx.lifecycle.*
import com.churickoid.filmity.data.DEFAULT_STATE
import com.churickoid.filmity.data.INTERNET_ERROR
import com.churickoid.filmity.data.LOADING_STATE
import com.churickoid.filmity.data.TOKEN_ERROR
import com.churickoid.filmity.domain.entity.ActionsAndMovie
import com.churickoid.filmity.domain.entity.MovieExtra
import com.churickoid.filmity.domain.usecases.movie.GetMoreInformationUseCase
import com.churickoid.filmity.presentation.screen.info.InfoFragment.Companion.ARG_MOVIE
import com.churickoid.filmity.presentation.tools.Event
import kotlinx.coroutines.launch
import retrofit2.HttpException
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
                _state.value = DEFAULT_STATE
            }
            catch (e: UnknownHostException) {
                _state.value = ERROR_STATE
                _error.value = Event(INTERNET_ERROR)
            }
            catch (e: HttpException) {
                _state.value = ERROR_STATE
                _error.value = Event(TOKEN_ERROR)
            }
            catch (e:Exception){
                _state.value = ERROR_STATE
                _error.value = Event(e.toString())
            }
        }
    }

    fun setNewRating(rating: Int, inWatchlist: Boolean){
        _actionsAndMovie.value = _actionsAndMovie.value!!.copy(userRating = rating, inWatchlist = inWatchlist)
    }

    companion object {
        const val ERROR_STATE = 2
    }




}