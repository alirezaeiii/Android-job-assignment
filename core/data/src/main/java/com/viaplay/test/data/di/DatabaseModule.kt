package com.viaplay.test.data.di

import android.content.Context
import androidx.room.Room
import com.viaplay.test.data.database.AppDatabase
import com.viaplay.test.data.database.LinkEntityDao
import com.viaplay.test.data.database.SectionEntityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "android-job-assignment")
            .build()

    @Singleton
    @Provides
    fun provideLinkEntityDao(appDatabase: AppDatabase): LinkEntityDao = appDatabase.linkDao()

    @Singleton
    @Provides
    fun provideSectionEntityDao(appDatabase: AppDatabase): SectionEntityDao = appDatabase.sectionDao()
}