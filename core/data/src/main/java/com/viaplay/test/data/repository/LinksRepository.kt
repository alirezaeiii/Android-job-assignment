package com.viaplay.test.data.repository

import android.content.Context
import com.viaplay.test.common.base.BaseRepository
import com.viaplay.test.data.api.BackendApi
import com.viaplay.test.data.database.LinkEntityDao
import com.viaplay.test.data.database.asDatabaseModel
import com.viaplay.test.data.database.asDomainModel
import com.viaplay.test.data.di.IoDispatcher
import com.viaplay.test.data.response.asDomainModel
import com.viaplay.test.data.utils.TimeProvider
import com.viaplay.test.domain.model.Link
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinksRepository @Inject constructor(
    private val backendApi: BackendApi,
    private val dao: LinkEntityDao,
    private val timeProvider: TimeProvider,
    @ApplicationContext context: Context,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : BaseRepository<List<Link>, Nothing, Nothing>(context, dispatcher) {

    override fun getCurrentTime(): Long = timeProvider.getCurrentTimeMillis()

    override suspend fun query(queryParam: Nothing?): List<Link> = dao.getAll().asDomainModel()

    override suspend fun fetch(fetchParam: Nothing?): List<Link> =
        backendApi.getDashboard().links.sections.asDomainModel()

    override suspend fun saveFetchResult(item: List<Link>) {
        dao.insertAll(item.asDatabaseModel())
    }
}