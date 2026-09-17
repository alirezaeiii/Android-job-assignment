package com.viaplay.test.common.utils

fun String.cleanHref() = this.replace("{?productsPerPage}", "")