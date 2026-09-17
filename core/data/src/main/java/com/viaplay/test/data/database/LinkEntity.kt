package com.viaplay.test.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.viaplay.test.domain.model.Link

@Entity(tableName = "link")
data class LinkEntity(
    @PrimaryKey val id: String,
    val title: String,
    val href: String
)

fun List<LinkEntity>.asDomainModel() = map(LinkEntity::asDomainModel)

fun List<Link>.asDatabaseModel() = map(Link::asDatabaseModel)

private fun LinkEntity.asDomainModel(): Link = Link(id = id, title = title, href = href)

private fun Link.asDatabaseModel(): LinkEntity = LinkEntity(id = id, title = title, href = href)