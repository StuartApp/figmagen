package com.stuart.figmagen.swift.swift.ui.extensions

import com.stuart.figmagen.Figmagen
import com.stuart.figmagen.Task
import com.stuart.figmagen.models.FileKey
import com.stuart.figmagen.tasks.ThemeFile
import com.stuart.figmagen.test.fakes.createTemporalDir
import com.stuart.figmagen.test.fakes.getResource
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest

internal class SwiftExtensionsTest {

    private val temporalDir = createTemporalDir()

    @Test
    fun `Dummy Swift Compose Colors`() = runTest {
        val swiftUiColorsTask: Task =
            SwiftUiColorsTask(
                checkColorCorrectness = true,
                outputPath = "${temporalDir.path}/DummyColors.actual.swift",
                ThemeFile("light", FileKey("UTHeBUbIE5aVyXXCLj48jp")),
                ThemeFile("dark", FileKey("JZoiFxRXtx0DH5RmzmbLLQ")),
            )

        val figmagen = Figmagen().apply { addTask(swiftUiColorsTask) }

        figmagen.generate()
        val actualFile = temporalDir.resolve("DummyColors.actual.swift")
        val expectedFile: File = getResource("DummyColors.expected.swift")
        assert(actualFile.readText().isNotBlank() && expectedFile.readText().isNotBlank())
        assertEquals(expectedFile.readText(), actualFile.readText())
    }
}
