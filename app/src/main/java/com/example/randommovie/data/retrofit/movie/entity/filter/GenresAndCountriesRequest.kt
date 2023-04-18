package com.example.randommovie.data.retrofit.movie.entity.filter

data class GenresAndCountriesRequest(
    val countries: List<CountryWithId>,
    val genres: List<GenreWithId>
)