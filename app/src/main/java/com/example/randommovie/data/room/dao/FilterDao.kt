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

    @Query("SELECT * FROM filter")
    suspend fun getFilter(): List<FilterDb>
}