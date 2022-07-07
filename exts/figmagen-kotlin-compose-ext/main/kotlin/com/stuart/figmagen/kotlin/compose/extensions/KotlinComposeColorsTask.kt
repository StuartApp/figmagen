package com.stuart.figmagen.kotlin.compose.extensions

import com.stuart.figmagen.kotlin.compose.extensions.internal.buildColorFile
import com.stuart.figmagen.models.Color
import com.stuart.figmagen.models.ColorFile
import com.stuart.figmagen.models.TaskFile
import com.stuart.figmagen.models.toTree
import com.stuart.figmagen.tasks.ColorsTask
import com.stuart.figmagen.tasks.ThemeFile
import kotlinx.coroutines.coroutineScope

/**
 * Implementation for generating a Kotlin file with all colors for Compose.
 *
 * @property packageName The package that the generated file will have at the top.
 * @property checkColorCorrectness If there are more than one theme, it can check all themes has the
 * same number and names of color tokens. If one is missing in one theme, it crashes indicating the
 * missing one/s.
 * @property outputPath The path where the file will be generated
 * @property themeFiles the list of figma files associated to a specific theme by name.
 */
public open class KotlinComposeColorsTask(
    private val packageName: String,
    private val checkColorCorrectness: Boolean,
    private val outputPath: String,
    vararg themeFiles: ThemeFile,
) : ColorsTask() {

    private val colors: MutableList<Color> = mutableListOf()

    internal open var contentColorFor: ((ColorFile) -> String?)? = null

    override val themeFiles: List<ThemeFile> = themeFiles.toList()

    override suspend fun run(): List<TaskFile> = coroutineScope {
        colors.addAll(getColors())

        if (checkColorCorrectness) checkThemesCorrectness(colors)

        val content = colors.toTree().buildColorFile(packageName, contentColorFor).toString()

        val sanitizedContent =
            buildList {
                    val colorQualifiedName = "androidx.compose.ui.graphics.Color"
                    for (line in content.lines()) {
                        val lineWithoutWhiteSpaces = line.filterNot(Char::isWhitespace)
                        when {
                            lineWithoutWhiteSpaces.startsWith("import kotlin.Unit") -> {}
                            !lineWithoutWhiteSpaces.startsWith("import") &&
                                lineWithoutWhiteSpaces.contains(colorQualifiedName) -> {
                                add(line.replace("androidx.compose.ui.graphics.Color", "Color"))
                            }
                            else -> add(line)
                        }
                    }
                }
                .joinToString("\n") { line ->
                    if (line.lastOrNull()?.isWhitespace() == true) line.dropLast(1) else line
                }

        listOf(
            TaskFile(outputPath, sanitizedContent),
        )
    }

    public fun contentColorFor(predicate: ((List<Color>, ColorFile) -> String?)) {
        contentColorFor = { predicate(colors, it) }
    }
}
