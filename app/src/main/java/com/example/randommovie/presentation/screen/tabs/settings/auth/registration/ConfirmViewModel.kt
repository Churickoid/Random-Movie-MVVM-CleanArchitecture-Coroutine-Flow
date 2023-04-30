package com.example.randommovie.presentation.screen.tabs.settings.auth.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.data.IncorrectCodeException
import com.example.randommovie.domain.usecases.account.ConfirmRegistrationUseCase
import com.example.randommovie.presentation.tools.Event
import kotlinx.coroutines.launch

class ConfirmViewModel(private val confirmRegistrationUseCase: ConfirmRegistrationUseCase) :
    ViewModel() {

    private val _closeFragment = MutableLiveData<Event<Unit>>()
    val closeFragment = _closeFragment

    fun confirmRegistration(email: String, password: String, code: Int) {
        viewModelScope.launch {
            try {
                confirmRegistrationUseCase(email, password, code)
                _closeFragment.value = Event(Unit)
            }catch (e:IncorrectCodeException){

            }

        }
    }



}