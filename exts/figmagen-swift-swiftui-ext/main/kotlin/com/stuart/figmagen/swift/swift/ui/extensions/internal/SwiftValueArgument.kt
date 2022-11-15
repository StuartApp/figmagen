package com.stuart.figmagen.swift.swift.ui.extensions.internal

import com.javiersc.kotlin.stdlib.isNotNullNorBlank

internal data class SwiftValueArgument(
    var name: String,
    var type: String,
    var defaultValue: String? = null
) {
    override fun toString(): String = buildString {
        val defaultValue = if (defaultValue.isNotNullNorBlank()) " = $defaultValue" else ""
        append("$name: $type$defaultValue")
    }
}

internal fun buildSwiftValueArgument(
    name: String,
    type: String,
    builder: SwiftValueArgument.() -> Unit = {}
): SwiftValueArgument {
    val valueArgument = SwiftValueArgument(name = name, type = type)
    builder(valueArgument)
    return valueArgument
}
