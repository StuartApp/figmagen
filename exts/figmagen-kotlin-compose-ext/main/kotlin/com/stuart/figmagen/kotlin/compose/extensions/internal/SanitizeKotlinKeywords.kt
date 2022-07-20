package com.stuart.figmagen.kotlin.compose.extensions.internal

internal fun String.sanitizeKotlinKeyword(): String {
    val keyword = kotlinKeywords.firstOrNull { it == this || "this.$it" == this }
    return if (keyword != null) this.replace(keyword, "`$keyword`") else this
}

private val kotlinKeywords = listOf("package")
