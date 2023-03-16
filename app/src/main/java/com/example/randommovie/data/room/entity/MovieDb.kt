package com.example.randommovie.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.randommovie.domain.entity.Movie


@Entity(
    tableName = "movies"
)
data class MovieDb(
    @PrimaryKey(autoGenerate = false) val id: Long,
    @ColumnInfo("title_main") val titleMain: String,
    @ColumnInfo("title_second") val titleSecond: String,
    @ColumnInfo("poster_url") val posterUrl: String,
    val year: Int?,
    @ColumnInfo("rating_kp") val ratingKP: Double,
    @ColumnInfo("rating_imdb") val ratingIMDB: Double,

) {

    fun toMovie(): Movie = Movie(
        this.id,
        this.titleMain,
        this.titleSecond,
        this.posterUrl,
        listOf(),
        listOf(),
        this.year,
        this.ratingKP,
        this.ratingIMDB
    )

    companion object{
        fun fromMovie(movie: Movie): MovieDb = MovieDb(
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