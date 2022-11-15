package com.stuart.figmagen.swift.swift.ui.extensions

import com.stuart.figmagen.models.Color
import com.stuart.figmagen.models.TaskFile
import com.stuart.figmagen.models.toTree
import com.stuart.figmagen.swift.swift.ui.extensions.internal.buildColorFile
import com.stuart.figmagen.tasks.ColorsTask
import com.stuart.figmagen.tasks.ThemeFile
import kotlinx.coroutines.coroutineScope

/**
 * Implementation for generating a Swift file with all colors for SwiftUI.
 *
 * @property checkColorCorrectness If there are more than one theme, it can check all themes has the
 * same number and names of color tokens. If one is missing in one theme, it crashes indicating the
 * missing one/s.
 * @property outputPath The path where the file will be generated
 * @property themeFiles the list of figma files associated to a specific theme by name.
 */
public open class SwiftUiColorsTask(
    private val checkColorCorrectness: Boolean,
    private val outputPath: String,
    vararg themeFiles: ThemeFile,
) : ColorsTask() {

    private val colors: MutableList<Color> = mutableListOf()

    override val themeFiles: List<ThemeFile> = themeFiles.toList()

    override suspend fun run(): List<TaskFile> = coroutineScope {
        colors.addAll(getColors())

        if (checkColorCorrectness) checkThemesCorrectness(colors)

        val content = colors.toTree().buildColorFile().toString()

        listOf(
            TaskFile(outputPath, content),
        )
    }
}
