package com.stuart.figmagen.tasks

import com.stuart.figmagen.models.FileKey

/**
 * A custom pair which match a theme with a Figma file.
 *
 * ```kotlin
 * val lightThemeFile = ThemeFile("light", "auAVu6zRJ39zECKv6hWDmH")
 * val darkThemeFile = ThemeFile("light", "wizOikAT1Wigtx6zHpfC87")
 * ```
 */
public data class ThemeFile(val theme: String, val fileKey: FileKey)
