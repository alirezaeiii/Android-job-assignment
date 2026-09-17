package com.viaplay.test

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.viaplay.test.common.utils.asLink
import com.viaplay.test.domain.model.Link

class LinkNavType : NavType<Link>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Link? =
        bundle.asLink(key, Link::class.java)

    override fun parseValue(value: String): Link {
        return Gson().fromJson(value, Link::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Link) {
        bundle.putParcelable(key, value)
    }
}