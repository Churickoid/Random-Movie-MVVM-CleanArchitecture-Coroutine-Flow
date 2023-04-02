package com.example.randommovie.presentation.screen

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.example.randommovie.R
import com.example.randommovie.domain.entity.Movie.Companion.RATING_NULL
import com.example.randommovie.domain.entity.ActionsAndMovie
import com.example.randommovie.presentation.screen.list.MovieListAdapter
import com.example.randommovie.presentation.tools.Event
import com.example.randommovie.presentation.tools.factory

open class BaseFragment : Fragment() {

    private val viewModel: BaseViewModel by viewModels{factory()}
    fun getRatingText(rating: Double): String {
        return if (rating== RATING_NULL) " — "
        else rating.toString()
    }


    fun showRatingDialogFragment(manager:FragmentManager, actionsAndMovie: ActionsAndMovie){
        RatingDialogFragment.show(manager,actionsAndMovie)
    }
    fun setupRatingDialogFragmentListener(manager:FragmentManager,action: (ActionsAndMovie) -> Unit) {
        RatingDialogFragment.setupListener(manager, this) {
            viewModel.addRatedMovie(it)
            action(it)
        }
    }

    fun toastError(eventMassage: Event<String>){
        eventMassage.getValue()?.let { massage ->
            Toast.makeText(requireContext(), massage, Toast.LENGTH_SHORT).show()
        }
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
fun RatingDialogFragment.getRatingColor(rating: Double,context : Context):Int = BaseFragment.getRatingColor(rating,context)