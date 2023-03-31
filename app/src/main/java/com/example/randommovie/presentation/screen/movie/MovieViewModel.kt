package com.example.randommovie.presentation.screen.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.domain.entity.Actions
import com.example.randommovie.domain.entity.ActionsAndMovie
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.usecases.filter.GetSearchFilterUseCase
import com.example.randommovie.domain.usecases.list.GetActionsByIdUseCase
import com.example.randommovie.domain.usecases.movie.GetLastMovieUseCase
import com.example.randommovie.domain.usecases.movie.GetRandomMovieUseCase
import com.example.randommovie.domain.usecases.movie.SetLastMovieUseCase
import com.example.randommovie.presentation.tools.Event
import kotlinx.coroutines.launch

class MovieViewModel(
    private val getRandomMovieUseCase: GetRandomMovieUseCase,
    private val searchFilterUseCase: GetSearchFilterUseCase,
    private val getLastMovieUseCase: GetLastMovieUseCase,
    private val setLastMovieUseCase: SetLastMovieUseCase,
    private val getActionsByIdUseCase: GetActionsByIdUseCase
) : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    var actions: Actions? = null

    private val _ratingActionsMovie = MutableLiveData<Event<ActionsAndMovie>>()
    val ratingActionsMovie: LiveData<Event<ActionsAndMovie>> = _ratingActionsMovie

    private val _infoActionsMovie = MutableLiveData<Event<ActionsAndMovie>>()
    val infoActionsMovie: LiveData<Event<ActionsAndMovie>> = _infoActionsMovie

    private val _buttonState = MutableLiveData<Int>()
    val buttonState: LiveData<Int> = _buttonState

    private val _error = MutableLiveData<Event<String?>>()
    val error: LiveData<Event<String?>> = _error

    private val _isFirst = MutableLiveData<Boolean>()
    val isFirst: LiveData<Boolean> = _isFirst

    init {
        viewModelScope.launch {
            val lastMovie = getLastMovieUseCase()
            if (lastMovie == null) _isFirst.value = true
            else {
                _movie.value = lastMovie!!
                _isFirst.value = false
            }
        }
    }

    fun getRandomMovie() {
        viewModelScope.launch {
            _buttonState.value = LOADING_STATE
            try {
                val movie = getRandomMovieUseCase(searchFilterUseCase())
                _movie.value = movie
                actions = null
                setLastMovieUseCase(movie)
                if (_isFirst.value!!) _isFirst.value = false
            } catch (e: Exception) {
                _error.value = Event(e.message)
            } finally {
                _buttonState.value = DEFAULT_STATE
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
        if (actions== null) actions = getActionsByIdUseCase(currentMovie.id)
        _buttonState.value = DEFAULT_STATE
        return ActionsAndMovie(currentMovie,actions!!.userRating,actions!!.inWatchlist)
    }
    companion object{
        const val LOADING_STATE = 0
        const val DEFAULT_STATE = 1
        const val DISABLED_STATE = 2
    }
}