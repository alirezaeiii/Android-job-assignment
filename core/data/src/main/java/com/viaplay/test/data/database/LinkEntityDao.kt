package com.viaplay.test.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LinkEntityDao {
    @Query("SELECT * FROM link")
    suspend fun getAll(): List<LinkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(properties: List<LinkEntity>)
}
