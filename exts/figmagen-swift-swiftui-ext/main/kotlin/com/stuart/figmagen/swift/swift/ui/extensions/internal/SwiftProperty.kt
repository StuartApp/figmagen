package com.stuart.figmagen.swift.swift.ui.extensions.internal

internal data class SwiftProperty(
    var visibility: SwiftVisibility = SwiftVisibility.Public,
    var mutability: SwiftMutability = SwiftMutability.Let,
    var name: String,
    var type: String,
    var value: String? = null,
    var isGetter: Boolean = false,
) {
    override fun toString(): String =
        buildString {
                val assignment = if (isGetter) "\nreturn".prependIndent("    ") else "="

                val name = name.sanitizeSwiftKeyword()

                val body: String? = value

                val property =
                    when {
                        body == null -> {
                            "$visibility $mutability $name: $type"
                        }
                        isGetter -> {
                            buildString {
                                appendLine("$visibility ${SwiftMutability.Var} $name: $type {")
                                appendLine("return $body".prependIndent("    "))
                                append("}")
                            }
                        }
                        else -> {
                            buildString {
                                appendLine("$visibility $mutability $name: $type $assignment")
                                append(body.prependIndent("    "))
                            }
                        }
                    }

                appendLine(property)
            }
            .lines()
            .joinToString("\n") { it.dropLastWhile(Char::isWhitespace) }
}

internal fun buildSwiftProperty(
    name: String,
    type: String,
    builder: SwiftProperty.() -> Unit = {}
): SwiftProperty {
    val property = SwiftProperty(name = name, type = type)
    builder(property)
    return property
}
