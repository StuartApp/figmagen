package com.stuart.figmagen.swift.swift.ui.extensions.internal

import com.stuart.figmagen.models.ColorFile
import com.stuart.figmagen.models.parentDirectories
import com.stuart.figmagen.tree.TreeNode
import java.util.Locale

internal fun TreeNode<ColorFile>.buildColorFile(): SwiftFile {
    val colorsClass = buildColorsStruct()
    val colorFile =
        buildSwiftFile(name = "Colors") {
            imports +=
                listOf(
                    "SwiftUI",
                )
            properties += buildAllThemeVariables()
            classes += colorsClass
        }

    return colorFile
}

internal fun TreeNode<ColorFile>.buildAllThemeVariables(): List<SwiftProperty> =
    mapNotNull { node -> (node.value as? ColorFile.Color)?.color?.theme }
        .toSet()
        .flatMap { theme ->
            mapNotNull { colorFileNode ->
                val isSameTheme = (colorFileNode.value as? ColorFile.Color)?.color?.theme == theme
                if (isSameTheme || !colorFileNode.value.isColor) {
                    colorFileNode.buildProperty(theme)
                } else {
                    null
                }
            }
        }

internal fun TreeNode<ColorFile>.buildProperty(theme: String): SwiftProperty {
    val colorFile = value
    val parents = value.parentDirectories.joinToString("", transform = String::capitalize)
    val body = buildString {
        val type = calculateType(colorFile)
        if (colorFile !is ColorFile.Color) {
            if (type == "Colors") {
                append(("$type("))
                appendLine()
            } else {
                appendLine(("$type("))
            }
            val sanitizedChildren =
                children.filter {
                    val nestedColorFile: ColorFile = it.value
                    nestedColorFile !is ColorFile.Color || nestedColorFile.color.theme == theme
                }
            sanitizedChildren
                .sortedBy { it.value.name }
                .forEachIndexed { index: Int, child: TreeNode<ColorFile> ->
                    val childName = child.value.name
                    val childParents =
                        child.value.parentDirectories.joinToString(
                            "",
                            transform = String::capitalize
                        )
                    val assignment = "$theme$childParents${childName.capitalize()}"
                    if (sanitizedChildren.lastIndex != index) {
                        appendLine(("$childName: $assignment,").prependIndent("    "))
                    } else {
                        appendLine(("$childName: $assignment").prependIndent("    "))
                    }
                }
            append(")")
        } else {
            val color = colorFile.color
            appendLine("Color(")
            appendLine("red: ${color.rgba.red},".prependIndent("    "))
            appendLine("green: ${color.rgba.green},".prependIndent("    "))
            appendLine("blue: ${color.rgba.blue},".prependIndent("    "))
            appendLine("opacity: ${color.rgba.alpha}".prependIndent("    "))
            append(")")
        }
    }

    val returnType = calculateType(colorFile)

    return buildSwiftProperty(
        name = "$theme$parents${colorFile.name.capitalize()}",
        type = returnType,
    ) {
        value = body
        visibility = if (returnType == "Colors") SwiftVisibility.Public else SwiftVisibility.Private
        isGetter = returnType == "Color"
    }
}

private fun calculateType(colorFile: ColorFile) =
    when {
        colorFile.isColor -> "Color"
        colorFile.isRoot -> "Colors"
        else -> {
            val nextDirs =
                colorFile.parentDirectories.joinToString(".", transform = String::capitalize)
            val currentDir = colorFile.name.capitalize()
            "Colors.$nextDirs.$currentDir".dropLastWhile { it == '.' }.replace("..", ".")
        }
    }

private fun TreeNode<ColorFile>.buildColorsStruct(
    swiftStruct: SwiftStruct = buildSwiftClass(name = "Colors"),
    contentColorFor: ((ColorFile) -> String?)? = null,
): SwiftStruct {
    swiftStruct.addProperties(this)
    swiftStruct.addNestedClasses(this)

    children.forEach { node ->
        val nestedStruct: SwiftStruct? = swiftStruct.findByColorFileOrNull(node.value)
        if (nestedStruct != null)
            node.buildColorsStruct(swiftStruct = nestedStruct, contentColorFor)
    }

    return swiftStruct
}

internal fun SwiftStruct.findByColorFileOrNull(colorFile: ColorFile): SwiftStruct? {
    val className = colorFile.name.capitalize()
    return when {
        this.classes.isEmpty() -> null
        this.classes.any { swiftStruct -> swiftStruct.name == className } -> {
            this.classes.first { swiftStruct -> swiftStruct.name == className }
        }
        else -> {
            this.classes
                .map { swiftStruct -> swiftStruct.findByColorFileOrNull(colorFile) }
                .firstOrNull()
        }
    }
}

internal fun SwiftStruct.addProperties(node: TreeNode<ColorFile>) {
    val arguments: List<ColorFile> = node.children.map(TreeNode<ColorFile>::value)
    properties +=
        arguments.distinctBy(ColorFile::name).sortedBy(ColorFile::name).map { colorFile ->
            buildSwiftProperty(
                name = colorFile.name,
                type = if (colorFile.isColor) "Color" else colorFile.name.capitalize(),
            ) {
                mutability = SwiftMutability.Let
            }
        }
}

internal fun SwiftStruct.addNestedClasses(node: TreeNode<ColorFile>) {
    val parentClass: SwiftStruct = this
    node.children
        .filter { it.value.isRoot || it.value.isDirectory }
        .forEach { childNode ->
            val childColorFile = childNode.value
            val nestedClass = buildSwiftClass(name = childColorFile.name.capitalize())
            parentClass.classes += nestedClass
        }
}

internal fun String.capitalize(): String = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}
