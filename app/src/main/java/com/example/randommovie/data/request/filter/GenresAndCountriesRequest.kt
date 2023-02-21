package com.example.randommovie.data.request.filter

data class GenresAndCountriesRequest(
    val countries: List<Country>,
    val genres: List<Genre>
){

}