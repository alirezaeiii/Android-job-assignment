package com.viaplay.test.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Link(
    val id: String,
    val title: String,
    val href: String
): Parcelable