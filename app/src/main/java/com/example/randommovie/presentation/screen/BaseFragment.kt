package com.example.randommovie.presentation.screen

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.randommovie.R
import com.example.randommovie.domain.entity.Movie.Companion.RATING_NULL
import com.example.randommovie.presentation.screen.list.MovieListAdapter

open class BaseFragment : Fragment() {


    fun getRatingText(rating: Double): String {
        return if (rating== RATING_NULL) " — "
        else rating.toString()
    }
    companion object{
        fun getRatingColor(rating: Double,context: Context): Int {
            return when {
                rating == RATING_NULL -> context.getColor(R.color.gray)
                rating < 5.0 -> context.getColor(R.color.red)
                rating < 7.0 -> context.getColor(R.color.gray)
                else -> context.getColor(R.color.green)
            }
        }
    }
}

fun MovieListAdapter.getRatingColor(rating: Double,context : Context):Int = BaseFragment.getRatingColor(rating,context)