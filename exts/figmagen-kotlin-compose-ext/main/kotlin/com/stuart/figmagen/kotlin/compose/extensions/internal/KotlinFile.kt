package com.stuart.figmagen.kotlin.compose.extensions.internal

internal data class KotlinFile(
    val name: String,
    val packageName: String,
    val imports: MutableList<String> = mutableListOf(),
    val properties: MutableList<KotlinProperty> = mutableListOf(),
    val functions: MutableList<KotlinFunction> = mutableListOf(),
    val classes: MutableList<KotlinClass> = mutableListOf(),
) {
    override fun toString(): String {
        val lines =
            buildString {
                    appendLine("package $packageName")
                    appendLine()

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

internal fun buildKotlinFile(
    name: String,
    packageName: String,
    builder: KotlinFile.() -> Unit = {},
): KotlinFile {
    val file = KotlinFile(name = name, packageName = packageName)
    builder(file)
    return file
}

internal fun String.indented(): List<String> = lines().map { it.prependIndent() }
