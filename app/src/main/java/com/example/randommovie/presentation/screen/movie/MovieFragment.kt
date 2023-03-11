package com.example.randommovie.presentation.screen.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentMovieBinding
import com.example.randommovie.presentation.screen.BaseFragment
import com.example.randommovie.presentation.screen.info.InfoFragment.Companion.ARG_MOVIE
import com.example.randommovie.presentation.tools.factory

class MovieFragment : BaseFragment() {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel: MovieViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
            findNavController().navigate(
                R.id.action_movieFragment_to_informationMovieFragment,
                bundleOf(ARG_MOVIE to viewModel.getCurrentMovie())
            )
        }

        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            val year = movie.releaseDate?.toString() ?: "—"

            binding.titleMainTextView.text = "${movie.titleMain} ($year)"
            binding.titleExtraTextView.text = movie.titleSecond
            binding.genresTextView.text = movie.genre.joinToString(separator = ", ")
            binding.kinopoiskRateTextView.text = getRatingText(movie.ratingKP)
            binding.imdbRateTextView.text = getRatingText(movie.ratingIMDB)

            binding.kinopoiskRateTextView.setTextColor(getRatingColor(movie.ratingKP,requireContext()))
            binding.imdbRateTextView.setTextColor(getRatingColor(movie.ratingIMDB,requireContext()))

            Glide.with(this@MovieFragment)
                .load(movie.posterUrl)
                .skipMemoryCache(true)
                .into(binding.posterImageView)

        }

        viewModel.buttonState.observe(viewLifecycleOwner) {
            if (it) changeStateButton(View.VISIBLE, View.INVISIBLE)
            else changeStateButton(View.INVISIBLE, View.VISIBLE)

        }
        viewModel.error.observe(viewLifecycleOwner) {
            it.getValue()?.let { massage ->
                Toast.makeText(requireContext(), massage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun changeStateButton(buttonState: Int, progressState: Int) {
        binding.actionsGroup.visibility = buttonState
        binding.loadingProgressBar.visibility = progressState
    }
}