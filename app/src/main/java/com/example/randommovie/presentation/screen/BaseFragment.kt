package com.example.randommovie.presentation.screen

import androidx.fragment.app.Fragment
import com.example.randommovie.R

open class BaseFragment : Fragment() {

    fun getRatingColor(rating: Double?): Int {
        return when {
            rating == null -> requireContext().getColor(R.color.gray)
            rating < 5.0 -> requireContext().getColor(R.color.red)
            rating < 7.0 -> requireContext().getColor(R.color.gray)
            else -> requireContext().getColor(R.color.green)

        }

    }
}