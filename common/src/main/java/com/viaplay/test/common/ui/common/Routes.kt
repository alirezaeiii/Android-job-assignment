package com.viaplay.test.common.ui.common

sealed class Routes(val title: String) {
    object LINKS : Routes("links_screen")
    object Details : Routes("details_screen/{$LINK}")

    companion object Companion {
        const val LINK = "link"
    }
}