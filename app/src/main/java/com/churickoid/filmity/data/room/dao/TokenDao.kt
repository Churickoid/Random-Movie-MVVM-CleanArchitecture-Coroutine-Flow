package com.churickoid.filmity.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.churickoid.filmity.data.room.entity.TokenDb
import kotlinx.coroutines.flow.Flow


@Dao
interface TokenDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToken(token : TokenDb)

    @Query("DELETE FROM tokens WHERE email = :email")
    suspend fun deleteTokenByEmail(email:String)

    @Query("SELECT * FROM tokens WHERE is_active = true LIMIT 1")
    suspend fun getActiveToken(): TokenDb?

    @Query("SELECT * FROM tokens ")
    fun getTokenList(): Flow<List<TokenDb>>

    @Query("UPDATE tokens SET is_active = :isActive WHERE email = :email")
    suspend fun changeIsActiveByEmail(isActive: Boolean, email:String)
}