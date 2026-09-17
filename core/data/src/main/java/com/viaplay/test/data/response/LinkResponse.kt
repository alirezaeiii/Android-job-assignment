package com.viaplay.test.data.response

import com.viaplay.test.domain.model.Link

data class LinkResponse(
    val id: String,
    val title: String,
    val href: String
)

fun List<LinkResponse>.asDomainModel() = map(LinkResponse::asDomainModel)

private fun LinkResponse.asDomainModel(): Link = Link(id = id, title = title, href = href)