package com.example.randommovie.presentation.screen.tabs.list


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.randommovie.domain.ListRepository.Companion.QUEUE_ORDER
import com.example.randommovie.domain.ListRepository.Companion.RATED_LIST_TYPE
import com.example.randommovie.domain.ListRepository.Companion.WATCH_LIST_TYPE
import com.example.randommovie.domain.usecases.list.DeleteMovieByIdUseCase
import com.example.randommovie.domain.usecases.list.GetMovieListByFiltersUseCase
import com.example.randommovie.domain.usecases.list.GetMoviesCountByTypeUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class ListViewModel(
    private val getMovieListByFiltersUseCase: GetMovieListByFiltersUseCase,
    private val deleteMovieByIdUseCase: DeleteMovieByIdUseCase,
    getMoviesCountByTypeUseCase: GetMoviesCountByTypeUseCase
) : ViewModel() {
    val type = MutableStateFlow(WATCH_LIST_TYPE)
    val order = MutableStateFlow(QUEUE_ORDER)
    private val isAsc = MutableStateFlow(true)

    val watchlistCounter = getMoviesCountByTypeUseCase(WATCH_LIST_TYPE).asLiveData()
    val ratedCounter = getMoviesCountByTypeUseCase(RATED_LIST_TYPE).asLiveData()

    @OptIn(ExperimentalCoroutinesApi::class)
    val movieList = combine(
        type,
        order,
        isAsc
    ) { type, order, isAsc ->
        Triple(type, order, isAsc)
    }.flatMapLatest { (type, order, isAsc) ->
        getMovieListByFiltersUseCase(type, order, isAsc)
    }.asLiveData()


    fun reverseAsc() {
        isAsc.value = !isAsc.value
    }

    fun deleteMovieById(id: Long) {
        viewModelScope.launch {
            deleteMovieByIdUseCase(id)
        }
    }
}

