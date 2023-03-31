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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentMovieBinding
import com.example.randommovie.domain.entity.ActionsAndMovie
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
            viewModel.getActionsAndMovieToInfo()
        }

        viewModel.infoActionsMovie.observe(viewLifecycleOwner){
            it.getValue()?.let { actionsAndMovie ->
                findNavController().navigate(
                    R.id.action_movieFragment_to_informationMovieFragment,
                    bundleOf(ARG_MOVIE to actionsAndMovie))
            }
        }

        binding.starButton.setOnClickListener {
            viewModel.getActionsAndMovieToRating()
        }

        viewModel.ratingActionsMovie.observe(viewLifecycleOwner){
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

            binding.kinopoiskRateTextView.setTextColor(getRatingColor(movie.ratingKP,requireContext()))
            binding.imdbRateTextView.setTextColor(getRatingColor(movie.ratingIMDB,requireContext()))

            Glide.with(this@MovieFragment)
                .load(movie.posterUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
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
        viewModel.isFirst.observe(viewLifecycleOwner) {
            if(it){
                binding.startTextView.visibility = View.VISIBLE
                binding.extraActionsGroup.visibility = View.INVISIBLE
                binding.movieGroup.visibility = View.INVISIBLE
            }else{
                binding.startTextView.visibility = View.INVISIBLE
                binding.extraActionsGroup.visibility = View.VISIBLE
                binding.movieGroup.visibility = View.VISIBLE
            }


        }

        setupRatingDialogFragmentListener(parentFragmentManager)
    }

    private fun changeStateButton(buttonState: Int, progressState: Int) {
        binding.actionsGroup.visibility = buttonState
        binding.loadingProgressBar.visibility = progressState
    }
}