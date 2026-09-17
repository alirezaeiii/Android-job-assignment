package com.viaplay.test.data.response

import com.google.gson.annotations.SerializedName

class LinksResponse(
    @SerializedName("viaplay:sections") val sections: List<LinkResponse>
)