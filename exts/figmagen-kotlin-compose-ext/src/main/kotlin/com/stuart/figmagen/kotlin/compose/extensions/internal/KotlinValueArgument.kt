package com.stuart.figmagen.kotlin.compose.extensions.internal

internal data class KotlinValueArgument(
    var visibility: KotlinVisibility = KotlinVisibility.Public,
    var mutability: KotlinMutability = KotlinMutability.Val,
    var name: String,
    var type: String,
    var defaultValue: String? = null
) {
    override fun toString(): String = buildString {
        val defaultValue = if (defaultValue?.isNotBlank() == true) " = $defaultValue" else ""
        val preName = if (mutability != KotlinMutability.Empty) "$visibility $mutability " else ""
        append("$preName$name: $type$defaultValue")
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
