package com.stuart.figmagen.kotlin.compose.extensions.internal

internal data class KotlinProperty(
    val annotations: MutableList<KotlinAnnotation> = mutableListOf(),
    var visibility: KotlinVisibility = KotlinVisibility.Public,
    var mutability: KotlinMutability = KotlinMutability.Val,
    var name: String,
    var returnType: String,
    var value: String,
    var isDelegate: Boolean = false,
    var isGetter: Boolean = false,
    var isPrivateSet: Boolean = false,
) {
    override fun toString(): String =
        buildString {
                val assignment =
                    when {
                        isDelegate -> "by"
                        isGetter -> "\nget() =\n".prependIndent()
                        else -> "="
                    }
                val body: String =
                    if (isGetter) {
                        value
                            .lines()
                            .mapIndexed { index, line ->
                                val indent: String = if (index == 0) "   " else "    "
                                line.prependIndent(indent)
                            }
                            .joinToString("\n")
                    } else {
                        value
                    }
                val propName = name.sanitizeKotlinKeyword()

                val property = "$visibility $mutability $propName: $returnType $assignment $body"
                appendLine(property)
                if (isPrivateSet) appendLine("private set".prependIndent())
            }
            .lines()
            .joinToString("\n") { it.dropLastWhile(Char::isWhitespace) }
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
