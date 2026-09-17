package com.viaplay.test.data.di

import com.viaplay.test.data.utils.DefaultTimeProvider
import com.viaplay.test.data.utils.TimeProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TimeModule {

    @Singleton
    @Binds
    abstract fun bindTimeProvider(timeProvider: DefaultTimeProvider): TimeProvider
}
