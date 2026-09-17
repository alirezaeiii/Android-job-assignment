package com.viaplay.test.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LinkEntity::class, SectionEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun linkDao(): LinkEntityDao

    abstract fun sectionDao(): SectionEntityDao
}