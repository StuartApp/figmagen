package com.stuart.figmagen.kotlin.compose.extensions

import com.stuart.figmagen.Figmagen
import com.stuart.figmagen.Task
import com.stuart.figmagen.kotlin.compose.extensions.fakes.contrastBasedContentColorFor
import com.stuart.figmagen.kotlin.compose.extensions.fakes.samples.dummyLightColors
import com.stuart.figmagen.models.FileKey
import com.stuart.figmagen.tasks.ThemeFile
import com.stuart.figmagen.test.fakes.createTemporalDir
import com.stuart.figmagen.test.fakes.getResource
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest

internal class KotlinComposeExtensionsTest {

    private val temporalDir = createTemporalDir()

    @Test
    fun `Dummy Kotlin Compose Colors`() = runTest {
        val kotlinComposeColorsTask: Task =
            KotlinComposeColorsTask(
                    packageName = "com.stuart.shared.ui.design.system.core",
                    checkColorCorrectness = true,
                    outputPath = "${temporalDir.path}/DummyColors.actual.kotlin",
                    ThemeFile("light", FileKey("UTHeBUbIE5aVyXXCLj48jp")),
                    ThemeFile("dark", FileKey("JZoiFxRXtx0DH5RmzmbLLQ")),
                )
                .apply { contentColorFor(::contrastBasedContentColorFor) }

        val figmagen = Figmagen().apply { addTask(kotlinComposeColorsTask) }

        figmagen.generate()
        val actualFile = File("$temporalDir/DummyColors.actual.kotlin")
        val expectedFile: File = getResource("DummyColors.expected.kotlin")
        assert(actualFile.readText().isNotBlank() && expectedFile.readText().isNotBlank())
        assertEquals(expectedFile.readText(), actualFile.readText())
    }

    @Test
    fun `Mock Kotlin Compose Colors`() = runTest {
        val mockKotlinComposeColorsTask: Task =
            MockKotlinComposeColorsTask(
                    packageName = "com.stuart.shared.ui.design.system.core",
                    checkColorCorrectness = false,
                    outputPath = "${temporalDir.path}/MockColors.actual.kotlin",
                    colors = dummyLightColors,
                )
                .apply { contentColorFor(::contrastBasedContentColorFor) }

        val figmagen = Figmagen().apply { addTask(mockKotlinComposeColorsTask) }

        figmagen.generate()
        val actualFile = File("$temporalDir/MockColors.actual.kotlin")
        val expectedFile: File = getResource("MockColors.expected.kotlin")
        assert(actualFile.readText().isNotBlank() && expectedFile.readText().isNotBlank())
        assertEquals(expectedFile.readText(), actualFile.readText())
    }
}
