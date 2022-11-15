package com.stuart.figmagen.swift.swift.ui.extensions.internal

internal fun String.sanitizeSwiftKeyword(): String {
    val keyword = swiftKeywords.firstOrNull { it == this }
    return if (keyword != null) this.replace(keyword, "`$keyword`") else this
}

private val swiftKeywords = listOf("static")
