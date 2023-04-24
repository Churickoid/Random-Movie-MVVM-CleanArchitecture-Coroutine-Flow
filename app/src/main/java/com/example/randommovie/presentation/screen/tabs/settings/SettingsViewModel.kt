package com.example.randommovie.presentation.screen.tabs.settings

import android.util.Log
import androidx.lifecycle.*
import com.example.randommovie.domain.entity.Token
import com.example.randommovie.domain.usecases.token.DeleteTokenUseCase
import com.example.randommovie.domain.usecases.token.GetTokenListUseCase
import com.example.randommovie.domain.usecases.token.SetTokenUseCase
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

