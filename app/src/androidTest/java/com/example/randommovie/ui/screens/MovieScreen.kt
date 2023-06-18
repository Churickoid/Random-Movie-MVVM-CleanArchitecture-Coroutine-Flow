package com.example.randommovie.ui.screens

import com.kaspersky.kaspresso.screens.KScreen
import com.example.randommovie.R
import com.example.randommovie.presentation.screen.tabs.movie.MovieFragment
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.progress.KProgressBar
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object MovieScreen: KScreen<MovieScreen>() {
    override val layoutId: Int = R.layout.fragment_movie
    override val viewClass: Class<*> = MovieFragment::class.java

    val nextMovieButton = KButton{
        withId(R.id.nextMovieButton)
    }
    val moreInfoButton = KButton{
        withId(R.id.moreButton)
    }
    val starButton = KButton{
        withId(R.id.starButton)
    }

    val posterImageView = KImageView{
        withId(R.id.posterImageView)
    }

    val titleMainTextView = KTextView{
        withId(R.id.titleMainTextView)
    }

    val startTextView = KTextView{
        withId(R.id.startTextView)
    }
    val loadingProgressBar = KProgressBar{
        withId(R.id.loadingProgressBar)
    }


}