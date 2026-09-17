package com.viaplay.test.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.viaplay.test.domain.model.Section

@Entity(tableName = "section")
data class SectionEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String
)

fun SectionEntity.asDomainModel(): Section =
    Section(sectionId = id, title = title, description = description)

fun Section.asDatabaseModel(): SectionEntity =
    SectionEntity(id = sectionId, title = title, description = description)