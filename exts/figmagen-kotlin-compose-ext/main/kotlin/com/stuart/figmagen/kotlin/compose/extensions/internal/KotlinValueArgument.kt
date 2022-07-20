package com.stuart.figmagen.kotlin.compose.extensions.internal

import com.javiersc.kotlin.stdlib.isNotNullNorBlank

internal data class KotlinValueArgument(
    var visibility: KotlinVisibility = KotlinVisibility.Public,
    var mutability: KotlinMutability = KotlinMutability.Val,
    var name: String,
    var type: String,
    var defaultValue: String? = null
) {
    override fun toString(): String = buildString {
        val defValue = defaultValue
        val defaultValue =
            if (defValue.isNotNullNorBlank()) " = ${defValue.sanitizeKotlinKeyword()}" else ""
        val preName = if (mutability != KotlinMutability.Empty) "$visibility $mutability " else ""
        val parameterName = "$preName${name.sanitizeKotlinKeyword()}"
        append("$parameterName: $type$defaultValue")
    }
}

internal fun buildKotlinValueArgument(
    name: String,
    type: String,
    builder: KotlinValueArgument.() -> Unit = {}
): KotlinValueArgument {
    val valueArgument = KotlinValueArgument(name = name, type = type)
    builder(valueArgument)
    return valueArgument
}
