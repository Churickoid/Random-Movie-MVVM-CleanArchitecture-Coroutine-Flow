package com.churickoid.filmity.ui.moviePage

import com.churickoid.filmity.ui.screens.MovieScreen
import com.churickoid.filmity.ui.utils.BaseTest
import org.junit.Test

class MovieTest: BaseTest(){

    @Test
    fun testGetMovie(){
        run {
            step("Проверка загрузки предыдущего фильма "){
                MovieScreen {
                    nextMovieButton {
                        isEnabled()
                    }
                    starButton{
                        isEnabled()
                    }
                    moreInfoButton{
                        isEnabled()
                    }
                    startTextView{
                        isInvisible()
                    }
                }
            }
            step("Запуск загрузки фильма"){
                MovieScreen{
                    nextMovieButton{
                        click()
                        isInvisible()
                    }
                    loadingProgressBar{
                        isVisible()
                    }
                }
            }
            step("Завершение загрузки фильма"){
                MovieScreen{
                    nextMovieButton {
                        isVisible()
                    }
                    posterImageView{
                        isVisible()
                    }
                    loadingProgressBar{
                        isInvisible()
                    }
                }
            }
        }

    }
}