package com.example.randommovie.presentation.screen.tabs.settings.auth.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommovie.data.INTERNET_ERROR
import com.example.randommovie.data.IncorrectCodeException
import com.example.randommovie.domain.usecases.account.ConfirmRegistrationUseCase
import com.example.randommovie.presentation.screen.tabs.settings.auth.login.LoginViewModel.Companion.SUCCESS
import com.example.randommovie.presentation.tools.Event
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class ConfirmViewModel(private val confirmRegistrationUseCase: ConfirmRegistrationUseCase) :
    ViewModel() {


    private val _codeState = MutableLiveData<Event<String>>()
    val codeState: LiveData<Event<String>> = _codeState

    private val _closeFragment = MutableLiveData<Event<Unit>>()
    val closeFragment = _closeFragment

    private val _toast = MutableLiveData<Event<String>>()
    val toast: LiveData<Event<String>> = _toast


    fun confirmRegistration(email: String, password: String, code: Int) {
        viewModelScope.launch {
            try {
                confirmRegistrationUseCase(email, password, code)
                _toast.value = Event(SUCCESS)
                _closeFragment.value = Event(Unit)
            } catch (e: IncorrectCodeException) {
                _codeState.value = Event(INCORRECT_CODE_ERROR)
            } catch (e: UnknownHostException) {
                _toast.value = Event(INTERNET_ERROR)
            }
        }
    }

    companion object {
        private const val INCORRECT_CODE_ERROR = "Incorrect code"
    }


}