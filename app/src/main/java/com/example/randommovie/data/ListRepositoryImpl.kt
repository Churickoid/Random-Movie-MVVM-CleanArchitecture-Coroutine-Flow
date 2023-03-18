package com.example.randommovie.data

import com.example.randommovie.data.room.dao.CountriesDao
import com.example.randommovie.data.room.dao.GenresDao
import com.example.randommovie.data.room.dao.MoviesDao
import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.entity.Movie

class ListRepositoryImpl(
    private val moviesDao: MoviesDao,
    private val countriesDao: CountriesDao,
    private val genresDao: GenresDao
) : ListRepository {
    override suspend fun getAllMovies(): List<Movie> {
        return moviesDao.getAllMovies().map {
                it.toMovie(
                    genresDao.getGenresByMovieId(it.id),
                    countriesDao.getCountriesByMovieId(it.id)
                )

        }
    }
}