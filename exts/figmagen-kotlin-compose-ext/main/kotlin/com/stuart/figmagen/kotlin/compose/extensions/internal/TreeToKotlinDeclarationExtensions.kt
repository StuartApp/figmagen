package com.stuart.figmagen.kotlin.compose.extensions.internal

import com.stuart.figmagen.models.ColorFile
import com.stuart.figmagen.models.parentDirectories
import com.stuart.figmagen.tree.TreeNode
import java.util.Locale

internal fun TreeNode<ColorFile>.buildColorFile(
    packageName: String,
    contentColorFor: ((ColorFile) -> String?)?
): KotlinFile {
    val colorsClass = buildColorsClass(contentColorFor = contentColorFor)
    val colorFile =
        buildKotlinFile(name = "Colors", packageName = packageName) {
            imports +=
                listOf(
                    "androidx.compose.runtime.Composable",
                    "androidx.compose.runtime.ReadOnlyComposable",
                    "androidx.compose.runtime.Stable",
                    "androidx.compose.runtime.getValue",
                    "androidx.compose.runtime.mutableStateOf",
                    "androidx.compose.runtime.setValue",
                    "androidx.compose.ui.graphics.Color",
                    "androidx.compose.ui.graphics.takeOrElse",
                )
            properties += buildAllThemeVariables()
            classes += colorsClass
        }

    return colorFile
}

internal fun TreeNode<ColorFile>.buildAllThemeVariables(): List<KotlinProperty> =
    mapNotNull { node -> (node.value as? ColorFile.Color)?.color?.theme }
        .toSet()
        .flatMap { theme -> map { colorFileNode -> colorFileNode.buildProperty(theme) } }

internal fun TreeNode<ColorFile>.buildProperty(theme: String): KotlinProperty {
    val colorFile = value
    val parents = value.parentDirectories.joinToString("", transform = String::capitalize)
    val body = buildString {
        appendLine()
        if (colorFile !is ColorFile.Color) {
            appendLine(("${colorFile.name.capitalize()}(").prependIndent())
            val sanitizedChildren =
                children.filter {
                    val nestedColorFile: ColorFile = it.value
                    nestedColorFile !is ColorFile.Color || nestedColorFile.color.theme == theme
                }
            for (child in sanitizedChildren) {
                val childName = child.value.name
                val childParents =
                    child.value.parentDirectories.joinToString("", transform = String::capitalize)
                appendLine(
                    ("$childName = $theme$childParents${childName.capitalize()},").prependIndent(
                        "        "
                    )
                )
            }
        } else {
            val color = colorFile.color
            appendLine("Color(".prependIndent())
            appendLine("red = ${color.rgba.red},".prependIndent("        "))
            appendLine("green = ${color.rgba.green},".prependIndent("        "))
            appendLine("blue = ${color.rgba.blue},".prependIndent("        "))
            appendLine("alpha = ${color.rgba.alpha},".prependIndent("        "))
        }
        append(")".prependIndent())
    }
    return buildKotlinProperty(
        name = "$theme$parents${colorFile.name.capitalize()}",
        returnType = if (colorFile.isColor) "Color" else colorFile.name.capitalize(),
        value = body,
    ) {
        visibility =
            if (returnType == "Colors") KotlinVisibility.Internal else KotlinVisibility.Private
    }
}

private fun TreeNode<ColorFile>.buildColorsClass(
    kclass: KotlinClass = buildKotlinClass(name = "Colors"),
    contentColorFor: ((ColorFile) -> String?)? = null,
): KotlinClass {
    kclass.annotations += buildKotlinAnnotation("Stable")
    kclass.addConstructorParameters(this)
    kclass.addUpdateFunction(this)
    kclass.addCopyFunction(this)
    kclass.addNestedClasses(this)
    kclass.addVariableStates(this)
    if (contentColorFor != null && this.value.isRoot) {
        kclass.addContentColorFunction(this, contentColorFor)
    }

    children.forEach { node ->
        val nestedClass: KotlinClass? = kclass.findByColorFileOrNull(node.value)
        if (nestedClass != null) node.buildColorsClass(kclass = nestedClass, contentColorFor)
    }

    return kclass
}

internal fun KotlinClass.findByColorFileOrNull(colorFile: ColorFile): KotlinClass? {
    val className = colorFile.name.capitalize()
    return when {
        this.classes.isEmpty() -> null
        this.classes.any { kclass -> kclass.name == className } -> {
            this.classes.first { kclass -> kclass.name == className }
        }
        else -> this.classes.map { kclass -> kclass.findByColorFileOrNull(colorFile) }.firstOrNull()
    }
}

internal fun KotlinClass.addConstructorParameters(node: TreeNode<ColorFile>) {
    val arguments: List<ColorFile> = node.children.map(TreeNode<ColorFile>::value)
    valueArguments +=
        arguments.distinctBy(ColorFile::name).map { colorFile ->
            buildKotlinValueArgument(
                name = colorFile.name,
                type = if (colorFile.isColor) "Color" else colorFile.name.capitalize(),
            ) {
                mutability = if (colorFile.isColor) KotlinMutability.Empty else KotlinMutability.Val
            }
        }
}

internal fun KotlinClass.addUpdateFunction(node: TreeNode<ColorFile>) {
    val parentClass = this
    val arguments: List<ColorFile> =
        node.children.map(TreeNode<ColorFile>::value).distinctBy(ColorFile::name)
    functions +=
        buildKotlinFunction(name = "update") {
            this.visibility = KotlinVisibility.Internal
            this.valueArguments += buildKotlinValueArgument("other", parentClass.name)
            this.body = buildString {
                for (argument in arguments) {
                    appendLine("${argument.name}.update(other.${argument.name})")
                }
            }
        }
}

internal fun KotlinClass.addCopyFunction(node: TreeNode<ColorFile>) {
    val parentClass = this
    val arguments: List<ColorFile> =
        node.children.map(TreeNode<ColorFile>::value).distinctBy(ColorFile::name)
    functions +=
        buildKotlinFunction(name = "copy") {
            this.valueArguments +=
                arguments.map { colorFile: ColorFile ->
                    val type = if (colorFile.isColor) "Color" else colorFile.name.capitalize()
                    buildKotlinValueArgument(colorFile.name, type = type) {
                        defaultValue = "this.${colorFile.name}"
                    }
                }
            this.visibility = KotlinVisibility.Internal
            this.returnType = parentClass.name
            this.body = buildString {
                appendLine("return ${parentClass.name}(")
                for (argument in arguments) {
                    appendLine("${argument.name} = ${argument.name}.copy(),".prependIndent())
                }
                appendLine(")")
            }
        }
}

internal fun KotlinClass.addNestedClasses(node: TreeNode<ColorFile>) {
    val parentClass: KotlinClass = this
    node.children
        .filter { it.value.isRoot || it.value.isDirectory }
        .forEach { childNode ->
            val childColorFile = childNode.value
            val nestedClass = buildKotlinClass(name = childColorFile.name.capitalize())
            parentClass.classes += nestedClass
        }
}

internal fun KotlinClass.addVariableStates(node: TreeNode<ColorFile>) {
    val parentClass: KotlinClass = this
    node.children
        .filter { it.value.isColor }
        .forEach { childNode ->
            val childColorFile = childNode.value
            val propertyState =
                buildKotlinProperty(
                    name = childColorFile.name,
                    returnType = "Color",
                    value = "mutableStateOf(${childColorFile.name})",
                ) {
                    mutability = KotlinMutability.Var
                    isDelegate = true
                    isPrivateSet = true
                }
            parentClass.properties += propertyState
        }
}

internal fun KotlinClass.addContentColorFunction(
    node: TreeNode<ColorFile>,
    predicate: ((ColorFile) -> String?),
) {
    val parentClass: KotlinClass = this
    val arguments =
        node
            .asSequence()
            .filter { it.value.isColor }
            .map { it.value }
            .filterIsInstance<ColorFile.Color>()
            .toList()

    val contentColorForFunction =
        buildKotlinFunction("contentColorFor") {
            valueArguments += buildKotlinValueArgument(name = "color", type = "Color")
            returnType = "Colors"
            body = buildString {
                appendLine("return when (color) {")
                for (argument in arguments) {
                    val parents = argument.parentDirectories.joinToString(".")
                    val property = argument.name
                    val newColor = predicate(argument)
                    if (newColor != null) {
                        val newLine = "$parents.$property -> $newColor".prependIndent()
                        if (!lines().contains(newLine)) appendLine(newLine)
                    }
                }
                appendLine("else -> Color.Unspecified".prependIndent())
                appendLine("}")
            }
        }
    parentClass.functions += contentColorForFunction
}

internal fun String.capitalize(): String = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}
