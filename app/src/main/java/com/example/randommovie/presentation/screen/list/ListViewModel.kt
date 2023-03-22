package com.example.randommovie.presentation.screen.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.data.ListRepositoryImpl.Companion.WATCHLIST_TYPE
import com.example.randommovie.domain.entity.UserInfoAndMovie
import com.example.randommovie.domain.usecases.list.DeleteMovieByIdUseCase
import com.example.randommovie.domain.usecases.list.GetMovieListByTypeUseCase
import kotlinx.coroutines.launch

class ListViewModel(
    private val getMovieListByTypeUseCase: GetMovieListByTypeUseCase,
    private val deleteMovieByIdUseCase: DeleteMovieByIdUseCase
) : ViewModel() {


    private val _movieList = MutableLiveData<List<UserInfoAndMovie>>()
    val movieList: LiveData<List<UserInfoAndMovie>> = _movieList

    init {
        getMovieList(WATCHLIST_TYPE)
    }

    fun deleteMovieById(id: Long){
        viewModelScope.launch {
            deleteMovieByIdUseCase(id)
        }
    }

     fun getMovieList(type: Int) {
        viewModelScope.launch{
            getMovieListByTypeUseCase(type).collect{
                _movieList.value = it
            }
        }
    }
}