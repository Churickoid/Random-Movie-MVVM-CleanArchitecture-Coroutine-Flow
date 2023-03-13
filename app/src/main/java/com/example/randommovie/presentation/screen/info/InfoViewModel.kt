package com.example.randommovie.presentation.screen.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.domain.entity.MovieExtra
import com.example.randommovie.domain.usecases.info.GetMoreInformationUseCase
import com.example.randommovie.presentation.tools.Event
import kotlinx.coroutines.launch

class InfoViewModel(private val getMoreInformationUseCase: GetMoreInformationUseCase): ViewModel() {

    private val _movieInfo = MutableLiveData<MovieExtra>()
    val movieInfo : LiveData<MovieExtra> = _movieInfo

    private val _state = MutableLiveData(LOADING_STATE)
    val state: LiveData<String> = _state

    private val _load = MutableLiveData(Event(true))
    val load:LiveData<Event<Boolean>> = _load

     fun getMovieInfo(id: Long){
        viewModelScope.launch {
            _state.value = LOADING_STATE
            try {
                _movieInfo.value = getMoreInformationUseCase.invoke(id)
                _state.value = VALID_STATE
            }
            catch (e: Exception){
                _state.value = e.message
            }
        }
    }
    companion object{
        const val LOADING_STATE = "LOADING_STATE"
        const val VALID_STATE = "VALID_STATE"
    }
}