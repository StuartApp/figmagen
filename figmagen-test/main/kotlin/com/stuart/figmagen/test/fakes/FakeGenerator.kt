package com.stuart.figmagen.test.fakes

import com.stuart.figmagen.models.Color
import com.stuart.figmagen.models.ColorFile
import com.stuart.figmagen.models.parentDirectories as originalParentDirectories

public fun ColorFile.colorFileDirectory(name: String): ColorFile.Directory {
    val currentDirectory =
        when (val currentColorFile = this) {
            is ColorFile.Color,
            is ColorFile.Directory -> currentColorFile.name
            is ColorFile.Root -> null
        }
    val parentDirectories = originalParentDirectories + listOfNotNull(currentDirectory)
    return ColorFile.Directory(name = name, parentDirectories = parentDirectories)
}

@Suppress("unused")
public fun ColorFile.colorFileColor(color: Color): ColorFile.Color = ColorFile.Color(color = color)
