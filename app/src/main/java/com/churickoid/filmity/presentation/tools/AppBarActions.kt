package com.churickoid.filmity.presentation.tools

import androidx.fragment.app.Fragment

interface AppBarActions {
    fun changeColor(color: Int,colorDark: Int,colorBack:Int)
}


fun Fragment.changeColor(color: Int,colorDark: Int,colorBack:Int) {
    return (requireActivity() as AppBarActions).changeColor(color,colorDark, colorBack)
}
