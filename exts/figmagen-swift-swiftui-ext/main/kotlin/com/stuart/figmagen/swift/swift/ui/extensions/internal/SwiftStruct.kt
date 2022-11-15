package com.stuart.figmagen.swift.swift.ui.extensions.internal

internal class SwiftStruct(
    var visibility: SwiftVisibility = SwiftVisibility.Public,
    var name: String,
    val properties: MutableList<SwiftProperty> = mutableListOf(),
    val functions: MutableList<SwiftFunction> = mutableListOf(),
    val classes: MutableList<SwiftStruct> = mutableListOf()
) {
    override fun toString(): String = buildString {
        appendLine("$visibility struct $name {")
        if ((properties + functions + classes).isNotEmpty()) {
            appendLine()
            for (property in properties) {
                "$property".indented().forEach(::appendLine)
            }
            for (function in functions) {
                "$function".indented().forEach(::appendLine)
            }
            for (swiftClass in classes) {
                "$swiftClass".indented().forEach(::appendLine)
            }
        } else {
            appendLine()
        }
        appendLine("}")
    }
}

internal fun buildSwiftClass(name: String, builder: SwiftStruct.() -> Unit = {}): SwiftStruct {
    val swiftStruct = SwiftStruct(name = name)
    builder(swiftStruct)
    return swiftStruct
}
