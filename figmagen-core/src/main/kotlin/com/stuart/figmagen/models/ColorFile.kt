package com.stuart.figmagen.models

import java.util.Locale

/**
 * To allow manage a tree of colors easily, this sealed class indicates the different type of files
 * it has.
 *
 * There are three:
 *
 * - `ColorFile.Root`:
 * - the root one, its name should be `colors`
 * - `ColorFile.Directory`:
 * - Intermediate directory in Figma
 * - Includes a property `parentDirectories`.
 * - `ColorFile.Color`:
 * - The color in Figma
 * - Includes the `color: Color` property.
 *
 * ```markdown
 * colors (ColorFile.Root)
 * ├── primary (ColorFile.Directory)
 * │   ├── main (ColorFile.Color)
 * │   ├── soft (ColorFile.Color)
 * │   └── contrast (ColorFile.Directory)
 * │       ├── main (ColorFile.Color)
 * │       └── soft (ColorFile.Color)
 * └── secondary (ColorFile.Directory)
 *     ├── main (ColorFile.Color)
 *     └── soft (ColorFile.Color)
 * ```
 */
public sealed class ColorFile {
    public abstract val name: String

    public val isRoot: Boolean
        get() = this is Root

    public val isDirectory: Boolean
        get() = this is Directory

    public val isColor: Boolean
        get() = this is Color

    public fun asString(): String = name

    public data class Root(
        override val name: String = "colors",
        val parentDirectories: List<String> = emptyList(),
    ) : ColorFile()

    public data class Directory(
        public override val name: String,
        public val parentDirectories: List<String>,
    ) : ColorFile()

    public data class Color(
        public val color: com.stuart.figmagen.models.Color,
    ) : ColorFile() {

        public override val name: String = color.name
        public val parentDirectories: List<String> = color.directories
    }
}

public val ColorFile.parentDirectories: List<String>
    get() =
        when (val file = this) {
            is ColorFile.Root -> emptyList()
            is ColorFile.Directory -> file.parentDirectories
            is ColorFile.Color -> file.parentDirectories
        }

public fun ColorFile.toCamelCase(): String =
    "${parentDirectories.joinToString("") { dir -> dir.capitalize() }}${name.capitalize()}"

private fun String.capitalize(): String = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}
