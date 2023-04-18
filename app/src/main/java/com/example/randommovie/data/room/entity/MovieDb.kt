package com.example.randommovie.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.randommovie.domain.entity.Movie


@Entity(
    tableName = "movies",
    indices = [
        Index("movie_id", unique = true)
    ]
)
data class MovieDb(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo("movie_id") val movieId: Long,
    @ColumnInfo("title_main") val titleMain: String,
    @ColumnInfo("title_second") val titleSecond: String,
    @ColumnInfo("poster_url") val posterUrl: String,
    val year: Int?,
    @ColumnInfo("rating_kp") val ratingKP: Double,
    @ColumnInfo("rating_imdb") val ratingIMDB: Double,

)
{

    fun toMovie(genres: List<String>,countries: List<String>): Movie = Movie(
        id = this.movieId,
        titleMain = this.titleMain,
        titleSecond = this.titleSecond,
        posterUrl = this.posterUrl,
        genre = genres,
        country = countries,
        year = this.year,
        ratingKP = this.ratingKP,
        ratingIMDB = this.ratingIMDB
    )

    companion object{
        fun fromMovie(movie: Movie): MovieDb = MovieDb(
            0,
            movie.id,
            movie.titleMain,
            movie.titleSecond,
            movie.posterUrl,
            movie.year,
            movie.ratingKP,
            movie.ratingIMDB
        )
    }
}