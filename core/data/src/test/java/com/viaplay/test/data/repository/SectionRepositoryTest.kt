package com.viaplay.test.data.repository

import android.content.Context
import app.cash.turbine.test
import com.viaplay.test.common.utils.Async
import com.viaplay.test.data.api.BackendApi
import com.viaplay.test.data.database.SectionEntity
import com.viaplay.test.data.database.SectionEntityDao
import com.viaplay.test.data.response.SectionResponse
import com.viaplay.test.domain.model.Section
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
class SectionRepositoryTest {

    private val backendApi: BackendApi = mockk()
    private val dao: SectionEntityDao = mockk(relaxed = true)
    private val context: Context = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var repository: SectionRepository

    @Before
    fun setUp() {
        repository = SectionRepository(backendApi, dao, context, testDispatcher)
        every { context.getString(any()) } returns "Error message"
    }

    @Test
    fun `getResult with empty DB and successful fetch emits Loading and Success`() = runTest {
        val sectionId = "section1"
        val title = "Title"
        val description = "Description"
        val apiResponse = SectionResponse(sectionId, title, description)
        val entity = SectionEntity(sectionId, title, description)

        coEvery { dao.getSection(sectionId) } returnsMany listOf(null, entity)
        coEvery { backendApi.getSection(any()) } returns apiResponse

        repository.getResult(queryParam = sectionId, fetchParam = "url", forceRefresh = true).test {
            assertTrue(awaitItem() is Async.Loading)
            
            val success = awaitItem() as Async.Success
            assertEquals(sectionId, success.data.sectionId)
            assertEquals(title, success.data.title)
            
            coVerify { backendApi.getSection("url") }
            coVerify { dao.insert(any()) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getResult with data in DB and no force refresh emits Loading and Success from DB`() = runTest {
        val sectionId = "section1"
        val entity = SectionEntity(sectionId, "Title", "Desc")
        coEvery { dao.getSection(sectionId) } returns entity

        repository.getResult(queryParam = sectionId, fetchParam = "url", forceRefresh = false).test {
            assertTrue(awaitItem() is Async.Loading)
            
            val success = awaitItem() as Async.Success
            assertEquals(sectionId, success.data.sectionId)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getResult when fetch fails emits Error`() = runTest {
        val sectionId = "section1"
        coEvery { dao.getSection(sectionId) } returns null
        coEvery { backendApi.getSection(any()) } throws Exception("Network error")

        repository.getResult(queryParam = sectionId, fetchParam = "url", forceRefresh = true).test {
            assertTrue(awaitItem() is Async.Loading)
            
            val error = awaitItem() as Async.Error
            assertEquals("Error message", error.message)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
