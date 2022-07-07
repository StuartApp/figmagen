package com.stuart.figmagen.kotlin.compose.extensions

import com.stuart.figmagen.models.Color
import com.stuart.figmagen.models.ColorFile
import com.stuart.figmagen.models.FileKey
import com.stuart.figmagen.tasks.ThemeFile

/**
 * To avoid calling the Figma API and testing locally
 *
 * @property packageName The package that the generated file will have at the top.
 * @property checkColorCorrectness If there are more than one theme, it can check all themes has the
 * same number and names of color tokens. If one is missing in one theme, it crashes indicating the
 * missing one/s.
 * @property outputPath The path where the file will be generated.
 * @property colors list of colors.
 */
public class MockKotlinComposeColorsTask(
    packageName: String,
    checkColorCorrectness: Boolean = true,
    outputPath: String,
    private val colors: List<Color>
) : KotlinComposeColorsTask(packageName, checkColorCorrectness, outputPath) {

    override var contentColorFor: ((ColorFile) -> String?)? = null

    override val themeFiles: List<ThemeFile> = listOf(ThemeFile("light", FileKey("mock-file-key")))

    override suspend fun getColors(themeFile: ThemeFile): List<Color> = colors
}
