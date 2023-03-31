package com.example.randommovie.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.randommovie.domain.entity.Actions
import com.example.randommovie.domain.entity.ActionsAndMovie


@Entity(
    tableName = "user_actions_for_movie",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = MovieDb::class,
            parentColumns = ["movie_id"],
            childColumns = ["movie_id"],
            onDelete = androidx.room.ForeignKey.CASCADE,
            onUpdate = androidx.room.ForeignKey.CASCADE
        )],
    indices = [
        Index("movie_id", unique = true)
    ]
)
data class UserActionsForMovieDb(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo("movie_id") val movieId: Long,
    val rating: Int,
    @ColumnInfo("in_watchlist") val inWatchlist: Boolean
)
{
    fun toUserInfoAndMovie(movieDb: MovieDb,genres: List<String>,countries: List<String>): ActionsAndMovie{
        return ActionsAndMovie(
            movieDb.toMovie(genres,countries),
            this.rating,
            this.inWatchlist
        )
    }

    fun toActions(): Actions {
        return Actions(
            this.rating,
            this.inWatchlist
        )
    }
    companion object{

        fun fromUserInfoAndMovie(actionsAndMovie: ActionsAndMovie): UserActionsForMovieDb
        = UserActionsForMovieDb(
            0,
            actionsAndMovie.movie.id,
            actionsAndMovie.userRating,
            actionsAndMovie.inWatchlist
        )
    }
}