package com.stuart.figmagen.tasks

import com.stuart.figmagen.Task
import com.stuart.figmagen.internal.FigmaApi
import com.stuart.figmagen.internal.await
import com.stuart.figmagen.internal.baseRequest
import com.stuart.figmagen.internal.baseUrl
import com.stuart.figmagen.internal.client
import com.stuart.figmagen.internal.fileJsonAdapter
import com.stuart.figmagen.internal.getStyles
import com.stuart.figmagen.internal.nodeIdsAsQueryParameter
import com.stuart.figmagen.models.Color
import com.stuart.figmagen.models.FileKey
import com.stuart.figmagen.models.TaskFile
import com.stuart.figmagen.models.isColor
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import okhttp3.Request

/**
 * This task get the colors from a list of Figma files that has to be provided as instance of
 * `ThemeFile`.
 *
 * A `ThemeFile` associates a theme with a Figma file, for example
 *
 * ```kotlin
 * val lightThemeFile = ThemeFile("light", "auAVu6zRJ39zECKv6hWDmH")
 * val darkThemeFile = ThemeFile("light", "wizOikAT1Wigtx6zHpfC87")
 * ```
 *
 * There are default implementations of `ColorsTask` for multiple languages or frameworks, for
 * example for Kotlin Compose UI framework.
 */
public abstract class ColorsTask : Task() {

    public abstract val themeFiles: List<ThemeFile>

    public abstract override suspend fun run(): List<TaskFile>

    public suspend fun getColors(): List<Color> = coroutineScope {
        themeFiles
            .map { themeFile ->
                val (theme: String, fileKey: FileKey) = themeFile
                async {
                    val colors = getColors(themeFile = themeFile)
                    check(colors.isNotEmpty()) { "the file key `$fileKey` has no colors" }
                    theme to colors
                }
            }
            .awaitAll()
            .toMap()
            .values.flatten()
    }

    public open suspend fun getColors(themeFile: ThemeFile): List<Color> {
        val (theme: String, fileKey: FileKey) = themeFile

        println("Getting colors for the theme $theme and the fileKey `$fileKey`")

        val figmaStyle: List<FigmaApi.Style> = getStyles(fileKey.value, figmaToken)

        val request: Request =
            baseRequest(figmaToken)
                .url(
                    baseUrl
                        .newBuilder()
                        .addPathSegment(fileKey.value)
                        .addPathSegment("nodes")
                        .addQueryParameter("ids", figmaStyle.nodeIdsAsQueryParameter())
                        .build()
                )
                .build()

        val response = client.newCall(request).await()

        val content: String =
            checkNotNull(response.body?.string()) {
                "The request body for getting the Figma colors must not be null, check the file provided"
            }

        if (response.code == 200) {
            val data: FigmaApi.File =
                checkNotNull(fileJsonAdapter.fromJson(content)) {
                    "There were an issue parsing the content to `FigmaApi.File`"
                }

            val colors: List<Color> =
                data.nodes.mapNotNull { (_, value) ->
                    val color: FigmaApi.Color? = value.document.fills.firstOrNull()?.color
                    val path: String = value.document.name
                    val pathAsList: List<String> = path.split("/")
                    val isValidColor: Boolean =
                        color != null && pathAsList.size >= 3 && pathAsList.isColor

                    if (color != null && isValidColor) {
                        val rgba: Color.RGBA =
                            Color.RGBA(
                                red = color.r,
                                green = color.g,
                                blue = color.b,
                                alpha = color.a
                            )

                        Color(
                            path = path,
                            theme = theme,
                            rgba = rgba,
                        )
                    } else null
                }

            return colors
        } else error("There is an error: Status code: ${response.code}, body: ${response.body}")
    }

    public fun checkThemesCorrectness(colors: List<Color>) {
        val colorsByTheme: Map<String, List<Color>> = colors.groupBy(Color::theme)
        val numberOfThemes: Int = colorsByTheme.keys.size

        check(numberOfThemes > 1) {
            "To check the correctness of all themes is necessary to provide two or more themes"
        }

        val missingColors: List<Color> =
            colorsByTheme.values.flatten().mapNotNull { color ->
                if (colorsByTheme.all { (_, colors) -> colors.any { it.path == color.path } }) null
                else color
            }

        check(missingColors.isEmpty()) {
            """
                |The next colors are missing in some theme: 
                ${missingColors.joinToString("\n") { "|  - ${it.theme}: ${it.path}" } }
            """.trimMargin()
        }
    }
}
