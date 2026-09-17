package com.viaplay.test.data.di

import com.viaplay.test.common.base.BaseRepository
import com.viaplay.test.data.repository.LinksRepository
import com.viaplay.test.data.repository.SectionRepository
import com.viaplay.test.domain.model.Link
import com.viaplay.test.domain.model.Section
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    internal abstract fun bindLinksRepository(repository: LinksRepository): BaseRepository<List<Link>, Nothing, Nothing>

    @Singleton
    @Binds
    internal abstract fun bindSectionRepository(repository: SectionRepository): BaseRepository<Section, String, String>
}