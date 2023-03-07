package com.example.randommovie.presentation.screen.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentMovieBinding
import com.example.randommovie.presentation.screen.info.InfoFragment.Companion.ARG_MOVIE
import com.example.randommovie.presentation.tools.factory

class MovieFragment : Fragment() {

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
                R.id.action_movieFragment_to_informationFragment,
                bundleOf(ARG_MOVIE to viewModel.getCurrentMovie())
            )
        }

        viewModel.movie.observe(viewLifecycleOwner) {
            val year = it.releaseDate ?: "—"
            val secondTitle: String
            val firstTitle = if (it.titleRu == null) {
                secondTitle = "—"
                "${it.title} ($year)"
            } else {
                secondTitle = it.title ?: "—"
                "${it.titleRu} ($year)"
            }

            binding.titleMainTextView.text = firstTitle
            binding.titleExtraTextView.text = secondTitle
            binding.genresTextView.text = it.genre.joinToString(separator = ", ")
            binding.kinopoiskRateTextView.text = it.ratingKP?.toString() ?: " — "
            binding.imdbRateTextView.text = it.ratingIMDB?.toString() ?: " — "
            if (it.posterUrl != null) {
                Glide.with(this@MovieFragment)
                    .load(it.posterUrl)
                    .skipMemoryCache(true)
                    .into(binding.posterImageView)
            } else {
                binding.posterImageView.setImageResource(R.drawable.blanked_image)
            }
        }

        viewModel.buttonState.observe(viewLifecycleOwner) {
            if (it) changeStateButton(View.VISIBLE, View.INVISIBLE)
            else changeStateButton(View.INVISIBLE, View.VISIBLE)

        }
        viewModel.error.observe(viewLifecycleOwner) {
            it.getValue()?.let {massage -> Toast.makeText(requireContext(), massage, Toast.LENGTH_SHORT).show()}
        }
    }

    private fun changeStateButton(buttonState: Int, progressState: Int) {
        binding.actionsGroup.visibility = buttonState
        binding.loadingProgressBar.visibility = progressState
    }
}