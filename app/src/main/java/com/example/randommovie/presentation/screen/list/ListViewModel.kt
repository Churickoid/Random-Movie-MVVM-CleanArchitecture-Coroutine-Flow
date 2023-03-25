package com.example.randommovie.presentation.screen.list


import androidx.lifecycle.*
import com.example.randommovie.domain.ListRepository.Companion.RATED_TYPE
import com.example.randommovie.domain.ListRepository.Companion.WATCHLIST_TYPE
import com.example.randommovie.domain.usecases.list.DeleteMovieByIdUseCase
import com.example.randommovie.domain.usecases.list.GetMovieListByFiltersUseCase
import com.example.randommovie.domain.usecases.list.GetMoviesCountByTypeUseCase
import com.example.randommovie.presentation.tools.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class ListViewModel(
    private val getMovieListByFiltersUseCase: GetMovieListByFiltersUseCase,
    private val deleteMovieByIdUseCase: DeleteMovieByIdUseCase,
    private val getMoviesCountByTypeUseCase: GetMoviesCountByTypeUseCase
) : ViewModel() {


    val type = MutableStateFlow(WATCHLIST_TYPE)
    val order = MutableStateFlow(0)
    val isAsc = MutableStateFlow(true)

    val watchlistCounter = getMoviesCountByTypeUseCase(WATCHLIST_TYPE).asLiveData()
    val ratedCounter = getMoviesCountByTypeUseCase(RATED_TYPE).asLiveData()


    private val _toTop = MutableLiveData<Event<Boolean>>()
    val toTop:LiveData<Event<Boolean>> = _toTop


    val movieList = combine(
        type,
        order,
        isAsc
    ){ type, order, isAsc ->
        Triple(type, order, isAsc)
    }.flatMapLatest { (type,order,isAsc) ->
        getMovieListByFiltersUseCase(type,order,isAsc)
    }.asLiveData()




    fun deleteMovieById(id: Long) {
        viewModelScope.launch {
            deleteMovieByIdUseCase(id)
        }
    }
}

