package com.viaplay.test.data.response

import com.google.gson.annotations.SerializedName


class DashboardResponse(
    @SerializedName("_links") val links: LinksResponse
)

