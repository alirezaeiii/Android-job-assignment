package com.viaplay.test.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SectionEntityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: SectionEntity)

    @Query("SELECT * FROM section WHERE id=:id")
    suspend fun getSection(id: String): SectionEntity?
}