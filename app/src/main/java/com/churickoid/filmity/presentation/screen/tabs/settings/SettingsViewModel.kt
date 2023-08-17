package com.churickoid.filmity.presentation.screen.tabs.settings

import androidx.lifecycle.*
import com.churickoid.filmity.domain.entity.Token
import com.churickoid.filmity.domain.usecases.token.DeleteTokenUseCase
import com.churickoid.filmity.domain.usecases.token.GetTokenListUseCase
import com.churickoid.filmity.domain.usecases.token.SetTokenUseCase
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val setTokenUseCase: SetTokenUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase,
    getTokenListUseCase: GetTokenListUseCase
) : ViewModel() {


    private val _tokenList = MutableLiveData<List<Token>>()
    val tokenList:LiveData<List<Token>> = _tokenList

    private var spinnerPosition = 0

    init {
        viewModelScope.launch {
            getTokenListUseCase().collect{
                spinnerPosition = 0
                _tokenList.value = it
            }
        }
    }

    fun setToken(token:Token,position: Int){
        spinnerPosition = position
        setTokenUseCase(token)
    }

    fun getSpinnerPosition(): Int = spinnerPosition

    fun deleteToken(token: Token){
        viewModelScope.launch {
            deleteTokenUseCase(token)
        }
    }

}

