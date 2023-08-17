package com.churickoid.filmity.presentation.screen


import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.churickoid.filmity.R
import com.churickoid.filmity.domain.entity.ActionsAndMovie
import com.churickoid.filmity.domain.entity.Movie.Companion.RATING_NULL
import com.churickoid.filmity.presentation.tools.Event
import com.churickoid.filmity.presentation.tools.factory
import com.google.android.material.textfield.TextInputLayout


open class BaseFragment : Fragment() {

    val baseViewModel: BaseViewModel by activityViewModels { factory() }

    fun getRatingText(rating: Double): String {
        return if (rating == RATING_NULL) " — "
        else rating.toString()
    }

    fun showRatingDialogFragment(manager: FragmentManager, actionsAndMovie: ActionsAndMovie) {
        RatingDialogFragment.show(manager, actionsAndMovie)
    }

    fun setupRatingDialogFragmentListener(
        manager: FragmentManager, action: (ActionsAndMovie) -> Unit
    ) {
        RatingDialogFragment.setupListener(manager, this) {
            baseViewModel.addRatedMovie(it)
            action(it)
        }
    }

    fun toastError(eventMassage: Event<String>) {
        eventMassage.getValue()?.let { massage ->
            Toast.makeText(requireContext(), massage, Toast.LENGTH_SHORT).show()
        }
    }

    fun changeTextInputLayoutColor(textInputLayout: TextInputLayout,color: Int){
        val colorState = ColorStateList.valueOf(color)
        textInputLayout.hintTextColor =  colorState
        textInputLayout.boxStrokeColor = color
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

fun Fragment.findTopNavController(): NavController {
    val topLevelHost =
        requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment?
    return topLevelHost?.navController ?: findNavController()
}

object GlideLoader : RequestListener<Bitmap> {

    private lateinit var onComplete: (Palette) -> Unit

    operator fun invoke(onComplete: (Palette) -> Unit): GlideLoader {
        GlideLoader.onComplete = onComplete
        return this
    }

    override fun onLoadFailed(
        e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean
    ): Boolean {
        return false
    }

    override fun onResourceReady(
        resource: Bitmap,
        model: Any?,
        target: Target<Bitmap>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {

        val palette = Palette.Builder(resource)
        palette.generate {
            onComplete(it!!)
        }
        return false
    }
}
