package com.example.randommovie.presentation.screen.tabs.movie

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.graphics.ColorUtils
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.randommovie.R
import com.example.randommovie.data.DEFAULT_STATE
import com.example.randommovie.data.LOADING_STATE
import com.example.randommovie.databinding.FragmentMovieBinding
import com.example.randommovie.presentation.screen.BaseFragment
import com.example.randommovie.presentation.screen.GlideLoader
import com.example.randommovie.presentation.screen.findTopNavController
import com.example.randommovie.presentation.screen.info.InfoFragment.Companion.ARG_MOVIE
import com.example.randommovie.presentation.screen.info.InfoFragment.Companion.ARG_TITLE
import com.example.randommovie.presentation.screen.tabs.movie.MovieViewModel.Companion.DISABLED_STATE
import com.example.randommovie.presentation.screen.tabs.movie.MovieViewModel.Companion.FIRST_TIME_STATE
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
                findTopNavController().navigate(
                    R.id.infoFragment,
                    bundleOf(
                        ARG_MOVIE to actionsAndMovie,
                        ARG_TITLE to actionsAndMovie.movie.titleMain
                    )
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


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.appbar_movie, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_filter -> {
                        findNavController().navigate(R.id.action_movieFragment_to_filterFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.STARTED)


        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            val year = movie.year?.toString() ?: "—"

            binding.titleMainTextView.text =
                requireContext().getString(R.string.movie_with_year, movie.titleMain, year)
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
                    val color = it.mutedSwatch?.rgb
                    val colorMain = if (color != null) 255 shl 24 or (color and 0x00ffffff)
                    else 0xFF2276A0.toInt()
                    val colorBack = if (color != null) (20 shl 24 or (color and 0x00ffffff))
                    else 0xFFFFFF
                    val colorDark = ColorUtils.blendARGB(colorMain, Color.BLACK, 0.2f)
                    baseViewModel.setColor(colorMain)
                    changeColor(colorMain, colorDark, colorBack)
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
        viewModel.toast.observe(viewLifecycleOwner) {
            toastError(it)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            baseViewModel.color.collect {colorMain ->
                binding.nextMovieButton.backgroundTintList = ColorStateList.valueOf(colorMain)
                binding.moreButton.drawable.setTint(colorMain)
                binding.starButton.drawable.setTint(colorMain)
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