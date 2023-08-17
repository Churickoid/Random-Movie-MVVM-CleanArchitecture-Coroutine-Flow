package com.churickoid.filmity.data.room.dao

import androidx.room.*
import com.churickoid.filmity.data.room.entity.ItemDb
import com.churickoid.filmity.data.room.entity.ItemsForMoviesDb

@Dao
interface ItemsDao {

    @Query("SELECT * FROM items WHERE type = :type")
    suspend fun getAllItemsByType(type: Int) : List<ItemDb>

    @Insert
    suspend fun insertItem(item :ItemDb)

    @Query("SELECT items.name FROM items "
            +"JOIN items_for_movies "
            +"ON items.id = items_for_movies.item_id AND items.type = items_for_movies.type "
            +"WHERE items_for_movies.type = 0 AND items_for_movies.movie_id = :movieId "
    )
    suspend fun getGenresByMovieId(movieId: Long): List<String>

    @Query("SELECT items.name FROM items "
            +"JOIN items_for_movies "
            +"ON items.id = items_for_movies.item_id AND items.type = items_for_movies.type "
            +"WHERE items_for_movies.type = 1 AND items_for_movies.movie_id = :movieId "
    )
    suspend fun getCountriesByMovieId(movieId: Long): List<String>

    @Query("SELECT id FROM items WHERE name = :name")
    suspend fun getItemIdByName(name: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItemForMovie(itemsForMoviesDb: ItemsForMoviesDb)
}