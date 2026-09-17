package com.viaplay.test.data.repository

import android.content.Context
import app.cash.turbine.test
import com.viaplay.test.common.utils.Async
import com.viaplay.test.data.api.BackendApi
import com.viaplay.test.data.database.LinkEntity
import com.viaplay.test.data.database.LinkEntityDao
import com.viaplay.test.data.response.DashboardResponse
import com.viaplay.test.data.response.LinkResponse
import com.viaplay.test.data.response.LinksResponse
import com.viaplay.test.data.utils.TimeProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LinksRepositoryTest {

    private val backendApi: BackendApi = mockk()
    private val dao: LinkEntityDao = mockk(relaxed = true)
    private val context: Context = mockk()
    private val timeProvider: TimeProvider = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var repository: LinksRepository

    @Before
    fun setUp() {
        repository = LinksRepository(backendApi, dao, timeProvider, context, testDispatcher)
        every { context.getString(any()) } returns "Error message"
        every { timeProvider.getCurrentTimeMillis() } returns 1000L
    }

    @Test
    fun `getResult with empty DB and successful fetch emits Loading and Success`() = runTest {
        val entity = createLinkEntity("1", "Title 1")
        val apiResponse = DashboardResponse(
            links = LinksResponse(
                sections = listOf(
                    createLinkResponse("1", "Title 1")
                )
            )
        )
        // 1st call returns empty, 2nd call (after save) returns the entity
        coEvery { dao.getAll() } returnsMany listOf(emptyList(), listOf(entity))
        coEvery { backendApi.getDashboard() } returns apiResponse

        repository.getResult(forceRefresh = true).test {
            assertTrue(awaitItem() is Async.Loading)
            
            val success = awaitItem() as Async.Success
            assertEquals(1, success.data.size)
            assertEquals("1", success.data[0].id)
            
            coVerify { backendApi.getDashboard() }
            coVerify { dao.insertAll(any()) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getResult with data in DB and no force refresh emits Loading and Success from DB`() = runTest {
        val entity = createLinkEntity("1", "Title 1")
        coEvery { dao.getAll() } returns listOf(entity)
        
        // Mock non-expired cache: 1000 (now) - 0 (lastRefresh) <= 60000
        every { timeProvider.getCurrentTimeMillis() } returns 1000L

        repository.getResult(forceRefresh = false).test {
            assertTrue(awaitItem() is Async.Loading)
            
            val success = awaitItem() as Async.Success
            assertEquals(1, success.data.size)
            
            // Verify NO network call was made because forceRefresh is false and cache is valid
            coVerify(exactly = 0) { backendApi.getDashboard() }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getResult with data in DB and cache expired emits Loading Success from DB Loading Success from network`() = runTest {
        val entity = createLinkEntity("1", "Title 1")
        val apiResponse = DashboardResponse(
            links = LinksResponse(
                sections = listOf(
                    createLinkResponse("1", "Title 1")
                )
            )
        )
        
        coEvery { dao.getAll() } returns listOf(entity)
        coEvery { backendApi.getDashboard() } returns apiResponse
        
        // 1st call for isCacheExpired: 70000 - 0 > 60000 (expired)
        // 2nd call for lastRefreshTime = getCurrentTime(): 70001
        // 3rd call for isCacheExpired (if any): ...
        every { timeProvider.getCurrentTimeMillis() } returnsMany listOf(70000L, 70001L, 70002L)

        repository.getResult(forceRefresh = false).test {
            assertTrue(awaitItem() is Async.Loading)
            assertTrue(awaitItem() is Async.Success) // From DB
            assertTrue(awaitItem() is Async.Loading) // refreshing = true
            assertTrue(awaitItem() is Async.Success) // Final from network
            
            coVerify { backendApi.getDashboard() }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getResult when fetch fails emits Error`() = runTest {
        coEvery { dao.getAll() } returns emptyList()
        coEvery { backendApi.getDashboard() } throws Exception("Network error")

        repository.getResult(forceRefresh = true).test {
            assertTrue(awaitItem() is Async.Loading)
            
            val error = awaitItem() as Async.Error
            assertEquals("Error message", error.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun createLinkResponse(id: String, title: String) = LinkResponse(
        id = id,
        title = title,
        href = "http://example.com/$id"
    )

    private fun createLinkEntity(id: String, title: String) = LinkEntity(
        id = id,
        title = title,
        href = "http://example.com/$id"
    )
}
