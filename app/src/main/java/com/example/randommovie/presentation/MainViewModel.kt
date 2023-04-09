package com.example.randommovie.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val _color = MutableLiveData<Triple<Int,Int,Int>>()
    val color : LiveData<Triple<Int,Int,Int>> = _color

    fun setColor(color: Int, colorLight: Int,colorDark:Int) {
        _color.value = Triple(color, colorLight,colorDark)
    }
}