package com.example.randommovie.data.room.dao

import androidx.room.Insert
import androidx.room.Query
import com.example.randommovie.data.room.entity.FilterDb


interface FilterDao {

    @Insert
    suspend fun insertFilter(filter : FilterDb)

    @Query("DELETE FROM filter")
    suspend fun deleteTable()
}