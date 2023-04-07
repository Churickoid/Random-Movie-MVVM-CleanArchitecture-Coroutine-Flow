package com.example.randommovie.presentation.screen.movie

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentMovieBinding
import com.example.randommovie.presentation.screen.BaseFragment
import com.example.randommovie.presentation.screen.GlideLoader
import com.example.randommovie.presentation.screen.info.InfoFragment.Companion.ARG_MOVIE
import com.example.randommovie.presentation.screen.movie.MovieViewModel.Companion.DEFAULT_STATE
import com.example.randommovie.presentation.screen.movie.MovieViewModel.Companion.DISABLED_STATE
import com.example.randommovie.presentation.screen.movie.MovieViewModel.Companion.FIRST_TIME_STATE
import com.example.randommovie.presentation.screen.movie.MovieViewModel.Companion.LOADING_STATE
import com.example.randommovie.presentation.tools.changeColor
import com.example.randommovie.presentation.tools.factory
import kotlinx.coroutines.launch


class MovieFragment : BaseFragment() {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel: MovieViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        binding.nextMovieButton.setOnClickListener {
            viewModel.getRandomMovie()
        }
        binding.moreButton.setOnClickListener {
            viewModel.getActionsAndMovieToInfo()
        }

        viewModel.infoActionsMovie.observe(viewLifecycleOwner) {
            it.getValue()?.let { actionsAndMovie ->
                findNavController().navigate(
                    R.id.action_movieFragment_to_informationMovieFragment,
                    bundleOf(ARG_MOVIE to actionsAndMovie)
                )
            }
        }

        binding.starButton.setOnClickListener {
            viewModel.getActionsAndMovieToRating()
        }

        viewModel.ratingActionsMovie.observe(viewLifecycleOwner) {
            it.getValue()?.let { actionsAndMovie ->
                showRatingDialogFragment(parentFragmentManager, actionsAndMovie)
            }
        }


        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            val year = movie.year?.toString() ?: "—"

            binding.titleMainTextView.text = "${movie.titleMain} ($year)"
            binding.titleExtraTextView.text = movie.titleSecond
            binding.genresTextView.text = movie.genre.joinToString(separator = ", ")
            binding.kinopoiskRateTextView.text = getRatingText(movie.ratingKP)
            binding.imdbRateTextView.text = getRatingText(movie.ratingIMDB)

            binding.kinopoiskRateTextView.setTextColor(
                getRatingColor(
                    movie.ratingKP, requireContext()
                )
            )
            binding.imdbRateTextView.setTextColor(
                getRatingColor(
                    movie.ratingIMDB, requireContext()
                )
            )

            binding.posterProgressBar.visibility = View.VISIBLE

            Glide.with(this@MovieFragment)
                .asBitmap()
                .load(movie.posterUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .listener(GlideLoader {
                    binding.posterProgressBar.visibility = View.INVISIBLE
                    val color = it.mutedSwatch?.rgb ?: R.color.purple_500
                    val colorMain = 255 shl 24 or (color and 0x00ffffff)
                    val colorBack = 20 shl 24 or (color and 0x00ffffff)
                    baseViewModel.setColor(colorMain, colorBack)
                })
                .into(binding.posterImageView)

        }

        viewModel.buttonState.observe(viewLifecycleOwner) {
            when (it) {
                LOADING_STATE -> changeVisibilityState(View.INVISIBLE, View.VISIBLE)
                DEFAULT_STATE -> {
                    changeVisibilityState(View.VISIBLE, View.INVISIBLE)
                    changeEnabledState(true)
                    if (binding.movieGroup.visibility == View.INVISIBLE) {
                        binding.movieGroup.visibility = View.VISIBLE
                        binding.startTextView.visibility = View.INVISIBLE
                    }
                }
                FIRST_TIME_STATE -> {
                    changeVisibilityState(View.VISIBLE, View.INVISIBLE)
                    binding.startTextView.visibility = View.VISIBLE
                    binding.movieGroup.visibility = View.INVISIBLE
                    binding.extraActionsGroup.visibility = View.INVISIBLE
                }
                DISABLED_STATE -> changeEnabledState(false)
            }

        }
        viewModel.error.observe(viewLifecycleOwner) {
            toastError(it)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            baseViewModel.color.collect { (colorMain, colorBack) ->
                val colorDark = ColorUtils.blendARGB(colorMain, Color.BLACK, 0.2f)

                binding.movieConstrantLayout.setBackgroundColor(colorBack)
                binding.nextMovieButton.backgroundTintList = ColorStateList.valueOf(colorMain)
                binding.moreButton.drawable.setTint(colorMain)
                binding.starButton.drawable.setTint(colorMain)
                changeColor(colorMain, colorBack, colorDark)

            }
        }

        setupRatingDialogFragmentListener(parentFragmentManager) {}
    }


    private fun changeVisibilityState(buttonState: Int, progressState: Int) {
        binding.actionsGroup.visibility = buttonState
        binding.loadingProgressBar.visibility = progressState
    }

    private fun changeEnabledState(isEnabled: Boolean) {
        binding.starButton.isEnabled = isEnabled
        binding.nextMovieButton.isEnabled = isEnabled
        binding.moreButton.isEnabled = isEnabled
    }
}