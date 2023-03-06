package com.example.randommovie.presentation.screen.info

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentInfoBinding
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.presentation.screen.info.InfoViewModel.Companion.LOADING_STATE
import com.example.randommovie.presentation.screen.info.InfoViewModel.Companion.VALID_STATE
import com.example.randommovie.presentation.tools.factory

class InfoFragment : Fragment() {

    private val viewModel: InfoViewModel by viewModels { factory() }
    private lateinit var binding: FragmentInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)
        val movie = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> requireArguments().getParcelable(
                ARG_MOVIE, Movie::class.java
            ) ?: throw IllegalArgumentException("error")
            else -> @Suppress("DEPRECATION")
            requireArguments().getParcelable(
                ARG_MOVIE
            ) ?: throw IllegalArgumentException("error")
        }
        viewModel.getMovieInfo(movie)
        binding.retryButton.setOnClickListener {
            viewModel.getMovieInfo(movie)
        }
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                LOADING_STATE -> changeState(View.VISIBLE, View.INVISIBLE, View.INVISIBLE)
                VALID_STATE -> changeState(View.INVISIBLE, View.VISIBLE, View.INVISIBLE)
                else -> {
                    changeState(View.INVISIBLE, View.INVISIBLE, View.VISIBLE)
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.movieInfo.observe(viewLifecycleOwner) {
            val secondTitle: String
            val firstTitle = if (movie.titleRu == null) {
                secondTitle = "—"
                movie.title
            } else {
                secondTitle = movie.title ?: "—"
                movie.titleRu
            }
            binding.titleMainTextView.text = firstTitle
            binding.titleExtraTextView.text = secondTitle
            binding.yearTextView.text = movie.releaseDate.toString()
            binding.genreTextView.text = movie.genre.toString()
            binding.countryTextView.text = movie.country.toString()
            binding.lengthTextView.text = it.length?.toString() ?: " — "
            binding.kinopoiskTextView.text = movie.ratingKP?.toString() ?: " — "
            binding.imdbTextView.text = movie.ratingIMDB?.toString() ?: " — "

            binding.detailsButton.setOnClickListener { TODO()}

            binding.descriptionTextView.text = it.description ?: "—"
            if (it.headerURL != null) {
                Glide.with(this@InfoFragment)
                    .load(it.headerURL)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(binding.headerImageView)
            } else {
                Glide.with(this@InfoFragment)
                    .load(movie.posterUrl)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(binding.headerImageView)

            }
        }


    }

    private fun changeState(loading: Int, info: Int, error: Int) {
        binding.loadingProgressBar.visibility = loading
        binding.infoLayout.visibility = info
        binding.retryButton.visibility = error
    }


    companion object {
        const val ARG_MOVIE = "ARG_MOVIE"
    }
}