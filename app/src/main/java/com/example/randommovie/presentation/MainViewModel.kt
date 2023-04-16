package com.example.randommovie.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val _color = MutableLiveData<Pair<Int,Int>>()
    val color : LiveData<Pair<Int,Int>> = _color

    fun setColor(color: Int, colorDark:Int) {
        _color.value = Pair(color,colorDark)
    }
}


