package com.example.randommovie.data.request.filter

data class GenresAndCountriesRequest(
    val countries: List<CountryWithId>,
    val genres: List<GenreWithId>
){

}