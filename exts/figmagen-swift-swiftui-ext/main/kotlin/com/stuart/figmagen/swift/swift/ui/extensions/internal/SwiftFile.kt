package com.stuart.figmagen.swift.swift.ui.extensions.internal

internal data class SwiftFile(
    val name: String,
    val imports: MutableList<String> = mutableListOf(),
    val properties: MutableList<SwiftProperty> = mutableListOf(),
    val functions: MutableList<SwiftFunction> = mutableListOf(),
    val classes: MutableList<SwiftStruct> = mutableListOf(),
) {
    override fun toString(): String {
        val lines =
            buildString {
                    imports.forEach { import -> appendLine("import $import") }
                    appendLine()
                    properties.forEach(::appendLine)
                    functions.forEach(::appendLine)
                    classes.forEach(::appendLine)
                }
                .lines()
                .dropLastWhile(String::isBlank)

        return lines
            .mapIndexedNotNull { index, line ->
                val previousLine = lines.getOrNull(index - 1)?.filterNot(Char::isWhitespace)
                val currentLine = line.filterNot(Char::isWhitespace)
                val nextLine = lines.getOrNull(index + 1)?.filterNot(Char::isWhitespace)

                if (previousLine == "}" && currentLine.isBlank() && nextLine == "}") null else line
            }
            .joinToString("\n") { line -> line.ifBlank { "" } } + "\n"
    }
}

internal fun buildSwiftFile(
    name: String,
    builder: SwiftFile.() -> Unit = {},
): SwiftFile {
    val file = SwiftFile(name = name)
    builder(file)
    return file
}

internal fun String.indented(): List<String> = lines().map { it.prependIndent() }
