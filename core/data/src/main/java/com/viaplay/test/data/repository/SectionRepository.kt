package com.viaplay.test.data.repository

import android.content.Context
import com.viaplay.test.common.base.BaseRepository
import com.viaplay.test.data.api.BackendApi
import com.viaplay.test.data.database.SectionEntityDao
import com.viaplay.test.data.database.asDatabaseModel
import com.viaplay.test.data.database.asDomainModel
import com.viaplay.test.data.di.IoDispatcher
import com.viaplay.test.data.response.asDomainModel
import com.viaplay.test.domain.model.Section
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SectionRepository @Inject constructor(
    private val backendApi: BackendApi,
    private val dao: SectionEntityDao,
    @ApplicationContext context: Context,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseRepository<Section, String, String>(context, dispatcher) {

    override suspend fun query(queryParam: String?): Section? =
        dao.getSection(queryParam!!)?.asDomainModel()

    override suspend fun fetch(fetchParam: String?): Section =
        backendApi.getSection(fetchParam!!).asDomainModel()

    override suspend fun saveFetchResult(item: Section) {
        dao.insert(item.asDatabaseModel())
    }
}