package com.example.randommovie.presentation.screen

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.randommovie.R
import com.example.randommovie.domain.entity.ActionsAndMovie
import com.example.randommovie.domain.entity.Movie.Companion.RATING_NULL
import com.example.randommovie.presentation.tools.Event
import com.example.randommovie.presentation.tools.factory

open class BaseFragment : Fragment() {

    private val viewModel: BaseViewModel by viewModels { factory() }
    fun getRatingText(rating: Double): String {
        return if (rating == RATING_NULL) " — "
        else rating.toString()
    }


    fun showRatingDialogFragment(manager: FragmentManager, actionsAndMovie: ActionsAndMovie) {
        RatingDialogFragment.show(manager, actionsAndMovie)
    }

    fun setupRatingDialogFragmentListener(
        manager: FragmentManager,
        action: (ActionsAndMovie) -> Unit
    ) {
        RatingDialogFragment.setupListener(manager, this) {
            viewModel.addRatedMovie(it)
            action(it)
        }
    }

    fun toastError(eventMassage: Event<String>) {
        eventMassage.getValue()?.let { massage ->
            Toast.makeText(requireContext(), massage, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun getRatingColor(rating: Double, context: Context): Int {
            return when {
                rating == RATING_NULL -> context.getColor(R.color.gray)
                rating < 5.0 -> context.getColor(R.color.red)
                rating < 7.0 -> context.getColor(R.color.gray)
                else -> context.getColor(R.color.green)
            }
        }
    }
}

object GlideLoader : RequestListener<Drawable> {

    private lateinit var onComplete: () -> Unit

    operator fun invoke(onComplete: () -> Unit): GlideLoader {
        GlideLoader.onComplete = { onComplete() }
        return this
    }

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        onComplete()
        return false
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        onComplete()
        return false
    }
}