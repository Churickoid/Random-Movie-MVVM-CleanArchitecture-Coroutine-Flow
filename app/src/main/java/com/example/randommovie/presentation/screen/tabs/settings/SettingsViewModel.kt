package com.example.randommovie.presentation.screen.tabs.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.randommovie.domain.entity.Token
import com.example.randommovie.domain.usecases.token.GetTokenListUseCase
import com.example.randommovie.domain.usecases.token.SetTokenUseCase

class SettingsViewModel(
    private val setTokenUseCase: SetTokenUseCase,
    getTokenListUseCase: GetTokenListUseCase
) : ViewModel() {


    val tokenList = getTokenListUseCase().asLiveData()

    private val _spinnerPosition = MutableLiveData<Int>()
    val spinnerPosition :LiveData<Int> = _spinnerPosition

    fun setToken(token:Token){
        setTokenUseCase(token)
    }
}

