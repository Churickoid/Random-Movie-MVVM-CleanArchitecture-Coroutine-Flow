package com.example.randommovie.presentation.tools

import androidx.fragment.app.Fragment

interface AppBarActions {

    fun changeTitle(label: String)

    fun changeColor(color: Int,colorLight: Int,colorDark: Int)
}

fun Fragment.changeTitle(label: String) {
    return (requireActivity() as AppBarActions).changeTitle(label)
}

fun Fragment.changeColor(color: Int,colorLight: Int,colorDark: Int) {
    return (requireActivity() as AppBarActions).changeColor(color,colorLight,colorDark)
}
