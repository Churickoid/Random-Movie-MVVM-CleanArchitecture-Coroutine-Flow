package com.example.randommovie.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.randommovie.data.room.entity.FilterDb
import com.example.randommovie.data.room.entity.ItemDb

@Dao
interface FilterDao {

    @Insert
    suspend fun insertFilter(filter : FilterDb)

    @Query("DELETE FROM filter")
    suspend fun deleteFilter()

    @Query("SELECT * FROM filter JOIN items "
            +"ON filter.item_id = items.id AND filter.item_type = items.type ")
    suspend fun getFilter(): Map<FilterDb,ItemDb>
}