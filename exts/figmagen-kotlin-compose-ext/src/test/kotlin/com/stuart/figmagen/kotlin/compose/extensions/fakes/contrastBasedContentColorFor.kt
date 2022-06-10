package com.stuart.figmagen.kotlin.compose.extensions.fakes

import com.stuart.figmagen.models.Color
import com.stuart.figmagen.models.ColorFile
import com.stuart.figmagen.models.parentDirectories

internal fun contrastBasedContentColorFor(colors: List<Color>, colorFile: ColorFile) =
    if (!colorFile.parentDirectories.contains("contrast") &&
            colors.any {
                it.directories == (colorFile.parentDirectories + "contrast") &&
                    it.name == colorFile.name
            }
    ) {
        "${colorFile.parentDirectories.joinToString(".")}.contrast.${colorFile.name}"
    } else null
