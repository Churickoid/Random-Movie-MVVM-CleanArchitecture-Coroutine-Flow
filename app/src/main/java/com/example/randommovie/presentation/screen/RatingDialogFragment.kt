package com.example.randommovie.presentation.screen

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.example.randommovie.databinding.DialogRatingBinding
import com.example.randommovie.domain.entity.ItemFilter
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.presentation.screen.filter.ListDialogFragment

class RatingDialogFragment : DialogFragment() {

    private val movie: Movie
        get() = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> requireArguments().getParcelable(
                ARG_MOVIE, Movie::class.java
            ) ?: throw IllegalArgumentException("Can't launch without list")
            else -> @Suppress("DEPRECATION")
            requireArguments().getParcelable(
                ARG_MOVIE
            ) ?: throw IllegalArgumentException("Can't launch without list")
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogRatingBinding.inflate(layoutInflater)

        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            binding.selectedRatingTextView.text = rating.toInt().toString()
            binding.selectedRatingTextView.background.setTint(getRatingColor(rating.toDouble(),requireContext()))
        }



        return AlertDialog.Builder(requireContext())
            .setPositiveButton("Apply") { _, _ ->
                callResult(binding.ratingBar.rating.toInt())
            }
            .setNeutralButton("Add to Watchlist") { _, _ ->
                callResult(0)
            }
            .setView(binding.root)
            .create()
    }

    private fun callResult(rating: Int){
        parentFragmentManager.setFragmentResult(
            TAG, bundleOf(KEY_MOVIE to movie,KEY_RATING to rating))
    }

    companion object{

        private val TAG = RatingDialogFragment::class.java.simpleName
        private const val KEY_RATING = "KEY_RATING"
        private const val KEY_MOVIE = "KEY_MOVIE"

        private const val ARG_MOVIE = "ARG_MOVIE"

        fun show(manager: FragmentManager,movie: Movie){
            val dialogFragment = RatingDialogFragment()
            dialogFragment.arguments = bundleOf(ARG_MOVIE to movie)
            dialogFragment.show(manager, TAG)
        }
        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (Movie,Int) -> Unit) {
            manager.setFragmentResultListener(TAG, lifecycleOwner){ _, result ->
                listener.invoke(result.getParcelable(KEY_MOVIE)!!,result.getInt(KEY_RATING))
            }
        }
    }
}