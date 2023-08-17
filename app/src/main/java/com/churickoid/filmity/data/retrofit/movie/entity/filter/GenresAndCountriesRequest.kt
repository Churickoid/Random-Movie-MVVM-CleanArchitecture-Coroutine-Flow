package com.churickoid.filmity.data.retrofit.movie.entity.filter

data class GenresAndCountriesRequest(
    val countries: List<CountryWithId>,
    val genres: List<GenreWithId>
)