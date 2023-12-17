package com.jeenatech.platform.ecommerce

import java.util.*


fun String.toSlug() = lowercase(Locale.getDefault())
    .replace("\n", " ")
    .split(" ")
    .joinToString("-")
    .replace("-+".toRegex(),"-")