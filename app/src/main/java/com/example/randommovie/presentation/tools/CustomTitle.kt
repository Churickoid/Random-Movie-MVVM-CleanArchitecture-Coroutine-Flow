package com.example.randommovie.presentation.tools

import androidx.fragment.app.Fragment

interface CustomTitle {

    fun changeTitle(label: String)
}

fun Fragment.changeTitle(label: String) {
    return (requireActivity() as CustomTitle).changeTitle(label)
}
