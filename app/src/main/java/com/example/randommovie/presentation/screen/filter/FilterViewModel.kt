package com.example.randommovie.presentation.screen.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.domain.FilterRepository
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.Movie
import kotlinx.coroutines.launch

class FilterViewModel(private val filterRepository: FilterRepository) : ViewModel() {

    private val _genres = MutableLiveData<List<ItemFilter>>()
    val genres: LiveData<List<ItemFilter>> = _genres

    private val _countries = MutableLiveData<List<ItemFilter>>()
    val countries: LiveData<List<ItemFilter>> = _countries
    fun getGenresList() {
        viewModelScope.launch {
            try {
                _genres.value = filterRepository.getGenresList()
            }catch (e:Exception){

            }
        }
    }


    companion object {
        val GENRES_LISTS = listOf(
            ItemFilter(1, "драма"),
            ItemFilter(2, "криминал"),
            ItemFilter(3, "мелодрама"),
            ItemFilter(4, "детектив"),
            ItemFilter(5, "фантастика"),
            ItemFilter(6, "приключения"),
            ItemFilter(7, "биография"),
            ItemFilter(8, "фильм-нуар"),
            ItemFilter(9, "вестерн"),
            ItemFilter(10, "боевик"),
            ItemFilter(11, "фэнтези"),
            ItemFilter(12, "комедия"),
            ItemFilter(13, "военный"),
            ItemFilter(14, "история"),
            ItemFilter(15, "музыка"),
            ItemFilter(16, "ужасы"),
            ItemFilter(17, "мультфильм"),
            ItemFilter(18, "семейный"),
            ItemFilter(19, "мюзикл"),
            ItemFilter(20, "спорт"),
            ItemFilter(21, "документальный"),
            ItemFilter(22, "короткометражка"),
            ItemFilter(23, "аниме"),
            ItemFilter(24, ""),
        )
    }
}