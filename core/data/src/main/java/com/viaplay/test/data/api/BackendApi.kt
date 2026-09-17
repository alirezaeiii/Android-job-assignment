package com.viaplay.test.data.api

import com.viaplay.test.data.response.DashboardResponse
import com.viaplay.test.data.response.SectionResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface BackendApi {
    @GET("androidexo-se")
    suspend fun getDashboard(): DashboardResponse

    @GET
    suspend fun getSection(@Url url: String): SectionResponse
}