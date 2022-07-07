package com.stuart.figmagen.test.fakes

import java.io.File
import kotlin.io.path.createTempDirectory

public fun Any.getResource(path: String): File =
    File(this.javaClass.classLoader.getResource(path).path)

public fun createTemporalDir(): File {
    val colorsDirectory =
        File("${System.getProperty("user.dir")}/build/generated/kotlin/colors").apply { mkdirs() }

    return createTempDirectory(directory = colorsDirectory.toPath()).toFile().apply {
        parentFile.mkdirs()
        createNewFile()
    }
}
