package com.viaplay.test.data.response

import com.viaplay.test.domain.model.Section

class SectionResponse(
    val sectionId: String,
    val title: String,
    val description: String
)

fun SectionResponse.asDomainModel(): Section =
    Section(sectionId = sectionId, title = title, description = description)