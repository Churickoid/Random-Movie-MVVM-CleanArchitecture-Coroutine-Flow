package com.example.randommovie.presentation.screen.info

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.randommovie.R
import com.example.randommovie.data.DEFAULT_STATE
import com.example.randommovie.data.LOADING_STATE
import com.example.randommovie.databinding.FragmentInfoBinding
import com.example.randommovie.domain.entity.ActionsAndMovie
import com.example.randommovie.presentation.screen.BaseFragment
import com.example.randommovie.presentation.screen.GlideLoader
import com.example.randommovie.presentation.screen.info.InfoViewModel.Companion.ERROR_STATE
import com.example.randommovie.presentation.tools.factory
import com.example.randommovie.presentation.tools.parcelable
import kotlinx.coroutines.launch


class InfoFragment : BaseFragment() {

    private val viewModel: InfoViewModel by viewModels { factory() }
    private lateinit var binding: FragmentInfoBinding

    private val actionsAndMovie: ActionsAndMovie
        get() = requireArguments().parcelable(ARG_MOVIE)
            ?: throw IllegalArgumentException("Can't work without movie")

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


        viewModel.actionsAndMovie.observe(viewLifecycleOwner){
            updateRating(it.userRating,it.inWatchlist)
        }


        val movie = actionsAndMovie.movie
        with(binding) {
            starButton.setOnClickListener {
                showRatingDialogFragment(parentFragmentManager,viewModel.actionsAndMovie.value!!)
            }
            titleMainTextView.text = movie.titleMain
            titleExtraTextView.text = movie.titleSecond
            yearTextView.text = movie.year?.toString() ?: " — "

            genreTextView.text = movie.genre.joinToString(separator = ", ")
            countryTextView.text = movie.country.joinToString(separator = ", ")

            kinopoiskRateTextView.text = getRatingText(movie.ratingKP)
            kinopoiskRateTextView.setTextColor(getRatingColor(movie.ratingKP,requireContext()))
            imdbRateTextView.text = getRatingText(movie.ratingIMDB)
            imdbRateTextView.setTextColor(getRatingColor(movie.ratingIMDB,requireContext()))

            retryButton.setOnClickListener {
                viewModel.getMovieInfo(actionsAndMovie.movie.id)
            }
        }
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                LOADING_STATE -> changeState(View.VISIBLE, View.INVISIBLE, View.INVISIBLE)
                DEFAULT_STATE -> changeState(View.INVISIBLE, View.VISIBLE, View.INVISIBLE)
                ERROR_STATE -> changeState(View.INVISIBLE, View.INVISIBLE, View.VISIBLE)
            }
        }
        viewModel.movieInfo.observe(viewLifecycleOwner) { movieExtra ->

            with(binding) {
                typeTextView.text = if (movieExtra.isMovie) "Фильм" else "Сериал"

                lengthTextView.text = parseTimeToString(movieExtra.length)

                imdbVoteTextView.text = movieExtra.imdbVoteCount.toString()
                kinopoiskVoteTextView.text = movieExtra.kinopoiskVoteCount.toString()


                binding.headerProgressBar.visibility = View.VISIBLE
                descriptionTextView.text = movieExtra.description ?: ""
                if (movieExtra.headerUrl != null) {
                    Glide.with(this@InfoFragment)
                        .asBitmap()
                        .load(movieExtra.headerUrl)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .listener(GlideLoader {
                            binding.headerProgressBar.visibility = View.INVISIBLE
                        })
                        .into(headerImageView)
                } else {
                    Glide.with(this@InfoFragment)
                        .asBitmap()
                        .load(movieExtra.posterUrlHQ)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .listener(GlideLoader{
                            binding.headerProgressBar.visibility = View.INVISIBLE

                        })
                        .into(headerImageView)

                }

                detailsImdbButton.isEnabled = (movieExtra.imdbId != null)
                if (!detailsImdbButton.isEnabled) detailsImdbButton.setBackgroundColor(requireContext().getColor(R.color.disabled_custom))
                detailsKpButton.setOnClickListener {
                    val urlTag = if (movieExtra.isMovie) "film" else "series"
                    val url = "https://www.kinopoisk.ru/$urlTag/${movie.id}"
                    createUrlIntent(url, "ru.kinopoisk")
                }
                detailsImdbButton.setOnClickListener {
                    val url = "https://www.imdb.com/title/${movieExtra.imdbId}/"
                    createUrlIntent(url, "com.imdb.mobile")
                }
            }

        }


        setupRatingDialogFragmentListener(parentFragmentManager){
            viewModel.setNewRating(it.userRating,it.inWatchlist)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            toastError(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            baseViewModel.color.collect { colorMain ->
                binding.retryButton.setBackgroundColor(colorMain)
                binding.detailsImdbButton.setBackgroundColor(colorMain)
                binding.detailsKpButton.setBackgroundColor(colorMain)
                binding.starButton.drawable.setTint(colorMain)
                binding.bookmarkImageView.drawable.setTint(colorMain)

            }
        }

    }

    private fun updateRating(rating: Int, inWatchlist: Boolean) {
        with(binding) {
            if (rating!=0) {
                yourRatingTextView.text = rating.toString()
                yourRatingTextView.background.setTint(
                    getRatingColor(rating.toDouble(), requireContext())
                )
                yourRatingTextView.visibility = View.VISIBLE
            }else yourRatingTextView.visibility = View.GONE
            if (inWatchlist) bookmarkImageView.visibility = View.VISIBLE
            else bookmarkImageView.visibility = View.INVISIBLE
        }
    }

    private fun createUrlIntent(url: String, appPackage: String) {
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
        binding.infoConstraintLayout.visibility = info
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
        const val ARG_TITLE = "titleMain"
    }
}