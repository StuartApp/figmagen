package com.stuart.figmagen.swift.swift.ui.extensions.internal

internal data class SwiftFunction(
    var visibility: SwiftVisibility = SwiftVisibility.Public,
    var name: String,
    val valueArguments: MutableList<SwiftValueArgument> = mutableListOf(),
    var returnType: String? = null,
    var body: String? = null,
) {
    override fun toString(): String = buildString {
        val funName = name
        val returningType = if (returnType != null) ": $returnType" else ""
        if (valueArguments.isEmpty()) {
            appendLine("$visibility func $funName()$returningType {")
        } else {
            appendLine("$visibility func $funName(")
            for (valueArgument in valueArguments) {
                appendLine("$valueArgument,".prependIndent())
            }
            appendLine(")$returningType {")
        }
        body?.indented()?.dropLastWhile(String::isBlank)?.forEach(::appendLine)
        appendLine("}")
    }
}

internal fun buildSwiftFunction(
    name: String,
    builder: SwiftFunction.() -> Unit = {}
): SwiftFunction {
    val function = SwiftFunction(name = name)
    builder(function)
    return function
}
