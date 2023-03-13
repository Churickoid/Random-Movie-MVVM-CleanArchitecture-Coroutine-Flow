package com.example.randommovie.presentation.screen

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.randommovie.databinding.DialogRatingBinding
import com.example.randommovie.presentation.screen.filter.ListDialogFragment

class RatingDialogFragment : DialogFragment() {

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
            TAG, bundleOf(KEY_RATING to rating))
    }

    companion object{

        private val TAG = RatingDialogFragment::class.java.simpleName
        private const val KEY_RATING = "KEY_RATING"

        fun show(manager: FragmentManager){
            val dialogFragment = RatingDialogFragment()
            dialogFragment.show(manager, TAG)
        }
        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (Int) -> Unit) {
            manager.setFragmentResultListener(TAG, lifecycleOwner){ _, result ->
                listener.invoke(result.getInt(KEY_RATING))
            }
        }
    }
}