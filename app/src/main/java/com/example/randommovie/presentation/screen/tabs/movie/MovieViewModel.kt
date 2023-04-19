package com.example.randommovie.presentation.screen.tabs.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.domain.entity.ActionsAndMovie
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.usecases.filter.GetSearchFilterUseCase
import com.example.randommovie.domain.usecases.list.GetActionsByIdUseCase
import com.example.randommovie.domain.usecases.movie.GetLastMovieUseCase
import com.example.randommovie.domain.usecases.movie.GetRandomMovieUseCase
import com.example.randommovie.domain.usecases.movie.SetLastMovieUseCase
import com.example.randommovie.presentation.tools.Event
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MovieViewModel(
    private val getRandomMovieUseCase: GetRandomMovieUseCase,
    private val getSearchFilterUseCase: GetSearchFilterUseCase,
    private val getLastMovieUseCase: GetLastMovieUseCase,
    private val setLastMovieUseCase: SetLastMovieUseCase,
    private val getActionsByIdUseCase: GetActionsByIdUseCase
) : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie


    private val _ratingActionsMovie = MutableLiveData<Event<ActionsAndMovie>>()
    val ratingActionsMovie: LiveData<Event<ActionsAndMovie>> = _ratingActionsMovie

    private val _infoActionsMovie = MutableLiveData<Event<ActionsAndMovie>>()
    val infoActionsMovie: LiveData<Event<ActionsAndMovie>> = _infoActionsMovie

    private val _buttonState = MutableLiveData<Int>()
    val buttonState: LiveData<Int> = _buttonState

    private val _toast = MutableLiveData<Event<String>>()
    val toast: LiveData<Event<String>> = _toast


    init {
        viewModelScope.launch {
            val lastMovie = getLastMovieUseCase()
            if (lastMovie == null) _buttonState.value = FIRST_TIME_STATE
            else {
                val lastMovieNotNull: Movie = lastMovie
                _movie.value = lastMovieNotNull
                _buttonState.value = DEFAULT_STATE
            }
        }
    }

    fun getRandomMovie() {
        viewModelScope.launch {
            val previousState = _buttonState.value!!
            _buttonState.value = LOADING_STATE
            try {
                val movie = getRandomMovieUseCase(getSearchFilterUseCase())
                _movie.value = movie
                setLastMovieUseCase(movie)
                _buttonState.value = DEFAULT_STATE
            }
            catch (e: UnknownHostException) {
                _toast.value = Event("Need internet connection")
                _buttonState.value = previousState
            }
            catch (e: Exception) {
                _toast.value = Event(e.toString())
                _buttonState.value = previousState
            }
        }
    }

    fun getActionsAndMovieToRating(){
        viewModelScope.launch {
            _ratingActionsMovie.value = Event(getActionForCurrentMovie())
        }
    }
    fun getActionsAndMovieToInfo(){
        viewModelScope.launch {
            _infoActionsMovie.value = Event(getActionForCurrentMovie())
        }
    }

    private suspend fun getActionForCurrentMovie(): ActionsAndMovie {
        _buttonState.value = DISABLED_STATE
        val currentMovie = movie.value!!
        val currentActions = getActionsByIdUseCase(currentMovie.id)
        _buttonState.value = DEFAULT_STATE
        return ActionsAndMovie(currentMovie,currentActions.userRating,currentActions.inWatchlist)
    }
    companion object{
        const val LOADING_STATE = 0
        const val DEFAULT_STATE = 1
        const val DISABLED_STATE = 2
        const val FIRST_TIME_STATE = 3
    }
}