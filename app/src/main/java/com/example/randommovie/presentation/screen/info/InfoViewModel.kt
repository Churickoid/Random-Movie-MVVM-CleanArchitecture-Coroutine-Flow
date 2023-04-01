package com.example.randommovie.presentation.screen.info

import androidx.lifecycle.*
import com.example.randommovie.domain.entity.ActionsAndMovie
import com.example.randommovie.domain.entity.MovieExtra
import com.example.randommovie.domain.usecases.movie.GetMoreInformationUseCase
import com.example.randommovie.presentation.screen.info.InfoFragment.Companion.ARG_MOVIE
import kotlinx.coroutines.launch

class InfoViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMoreInformationUseCase: GetMoreInformationUseCase
) : ViewModel() {

    private val _movieInfo = MutableLiveData<MovieExtra>()
    val movieInfo: LiveData<MovieExtra> = _movieInfo

    private val _state = MutableLiveData(LOADING_STATE)
    val state: LiveData<String> = _state

    private val _actionsAndMovie = savedStateHandle.getLiveData<ActionsAndMovie>(ARG_MOVIE)
    val actionsAndMovie: LiveData<ActionsAndMovie> = _actionsAndMovie

    init {
        getMovieInfo(_actionsAndMovie.value!!.movie.id)
    }


    fun getMovieInfo(id: Long) {
        viewModelScope.launch {
            _state.value = LOADING_STATE
            try {
                _movieInfo.value = getMoreInformationUseCase.invoke(id)
                _state.value = VALID_STATE
            } catch (e: Exception) {
                _state.value = e.message
            }
        }
    }

    fun setNewRating(rating: Int, inWatchlist: Boolean){
        _actionsAndMovie.value = _actionsAndMovie.value!!.copy(userRating = rating, inWatchlist = inWatchlist)
    }

    companion object {
        const val LOADING_STATE = "LOADING_STATE"
        const val VALID_STATE = "VALID_STATE"
    }


    //TODO Обработчик ошибок всем добавить для красоты =)
    /*private fun launchDataLoad(block: suspend () -> Unit){
        viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: TitleRefreshError) {
                _snackBar.value = error.message
            } finally {
                _spinner.value = false
            }
        }
        }
    }*/

}