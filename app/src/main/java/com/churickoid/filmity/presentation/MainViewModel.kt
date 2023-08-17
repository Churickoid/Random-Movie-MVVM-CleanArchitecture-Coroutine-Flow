package com.churickoid.filmity.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val _color = MutableLiveData<Triple<Int,Int,Int>>()
    val color : LiveData<Triple<Int,Int,Int>> = _color

    fun setColor(color: Int, colorDark:Int, colorBack: Int) {
        _color.value = Triple(color,colorDark,colorBack)
    }
}


