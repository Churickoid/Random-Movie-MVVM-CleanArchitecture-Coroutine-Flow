package com.example.randommovie.presentation.tools

import androidx.fragment.app.Fragment

interface AppBarActions {
    fun changeColor(color: Int,colorDark: Int)
}


fun Fragment.changeColor(color: Int,colorDark: Int) {
    return (requireActivity() as AppBarActions).changeColor(color,colorDark)
}
