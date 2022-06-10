package com.stuart.figmagen.kotlin.compose.extensions.internal

internal data class KotlinProperty(
    val annotations: MutableList<KotlinAnnotation> = mutableListOf(),
    var visibility: KotlinVisibility = KotlinVisibility.Public,
    var mutability: KotlinMutability = KotlinMutability.Val,
    var name: String,
    var returnType: String,
    var value: String,
    var isDelegate: Boolean = false,
    var isPrivateSet: Boolean = false,
) {
    override fun toString(): String = buildString {
        val assignmentSymbol = if (isDelegate) "by" else "="
        appendLine("$visibility $mutability $name: $returnType $assignmentSymbol $value")
        if (isPrivateSet) appendLine("private set".prependIndent())
    }
}

internal fun buildKotlinProperty(
    name: String,
    returnType: String,
    value: String,
    builder: KotlinProperty.() -> Unit = {}
): KotlinProperty {
    val property = KotlinProperty(name = name, returnType = returnType, value = value)
    builder(property)
    return property
}
