package com.example.randommovie.presentation.screen.info

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.randommovie.R
import com.example.randommovie.databinding.FragmentInfoBinding
import com.example.randommovie.domain.entity.Movie
import com.example.randommovie.presentation.screen.BaseFragment
import com.example.randommovie.presentation.screen.info.InfoViewModel.Companion.LOADING_STATE
import com.example.randommovie.presentation.screen.info.InfoViewModel.Companion.VALID_STATE
import com.example.randommovie.presentation.tools.changeTitle
import com.example.randommovie.presentation.tools.factory
import com.example.randommovie.presentation.tools.parcelable


class InfoFragment : BaseFragment() {

    private val viewModel: InfoViewModel by viewModels { factory() }
    private lateinit var binding: FragmentInfoBinding

    private val movie: Movie
        get() = requireArguments().parcelable(ARG_MOVIE)
            ?: throw IllegalArgumentException("Can't work without movie")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        changeTitle(movie.titleMain)

        return inflater.inflate(R.layout.fragment_info, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)


        binding.retryButton.setOnClickListener {
            viewModel.getMovieInfo(movie.id)
        }
        viewModel.load.observe(viewLifecycleOwner) {
            it.getValue()?.let { viewModel.getMovieInfo(movie.id) }
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
        viewModel.movieInfo.observe(viewLifecycleOwner) { movieExtra ->

            binding.titleMainTextView.text = movie.titleMain
            binding.titleExtraTextView.text = movie.titleSecond
            binding.typeTextView.text = if (movieExtra.isMovie) "Фильм" else "Сериал"
            binding.yearTextView.text = movie.year?.toString() ?: " — "

            binding.genreTextView.text = movie.genre.joinToString(separator = ", ")
            binding.countryTextView.text = movie.country.joinToString(separator = ", ")
            binding.lengthTextView.text = parseTimeToString(movieExtra.length)

            binding.kinopoiskRateTextView.text = getRatingText(movie.ratingKP)
            binding.imdbRateTextView.text = getRatingText(movie.ratingIMDB)

            binding.kinopoiskRateTextView.setTextColor(getRatingColor(movie.ratingKP,requireContext()))
            binding.imdbRateTextView.setTextColor(getRatingColor(movie.ratingIMDB,requireContext()))

            binding.imdbVoteTextView.text = movieExtra.imdbVoteCount.toString()
            binding.kinopoiskVoteTextView.text = movieExtra.kinopoiskVoteCount.toString()

            binding.descriptionTextView.text = movieExtra.description ?: ""
            if (movieExtra.headerUrl != null) {
                Glide.with(this@InfoFragment)
                    .load(movieExtra.headerUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.headerImageView)
            } else {
                Glide.with(this@InfoFragment)
                    .load(movieExtra.posterUrlHQ)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.headerImageView)

            }

            binding.detailsImdbButton.isEnabled = (movieExtra.imdbId != null)

            binding.detailsKpButton.setOnClickListener {
                val urlTag = if (movieExtra.isMovie) "film" else "series"
                val url = "https://www.kinopoisk.ru/$urlTag/${movie.id}"
                createUrlIntent(url,"ru.kinopoisk")
            }
            binding.detailsImdbButton.setOnClickListener {
                val url = "https://www.imdb.com/title/${movieExtra.imdbId}/"
                createUrlIntent(url,"com.imdb.mobile")
            }

        }


    }

    private fun createUrlIntent(url: String, appPackage: String){
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            intent.setPackage(appPackage)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                )
            )
        }
    }


    private fun changeState(loading: Int, info: Int, error: Int) {
        binding.loadingProgressBar.visibility = loading
        binding.infoLayout.visibility = info
        binding.retryButton.visibility = error
    }

    private fun parseTimeToString(time: Int?): String {
        if (time == null || time == 0) return " — "
        var string = ""
        if (time > 60) string += "${time / 60}:${String.format("%02d", time % 60)} / "
        string += "$time min"
        return string
    }

    companion object {
        const val ARG_MOVIE = "ARG_MOVIE"
    }
}