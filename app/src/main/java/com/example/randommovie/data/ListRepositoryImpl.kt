package com.example.randommovie.data

import com.example.randommovie.data.room.dao.MoviesDao
import com.example.randommovie.domain.ListRepository
import com.example.randommovie.domain.entity.Movie

class ListRepositoryImpl(private val moviesDao: MoviesDao) : ListRepository {
    override suspend fun getAllMovies(): List<Movie> {
        val moviesList = mutableListOf<Movie>()
        moviesDao.getAllMovies().forEach { moviesList.add(it.toMovie()) }
        return moviesList
    }
}