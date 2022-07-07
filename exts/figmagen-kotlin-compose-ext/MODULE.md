# Module figmagen-kotlin-compose-ext

## [KotlinComposeColorsTask](/main/kotlin/com/stuart/figmagen/kotlin/compose/extensions/KotlinComposeColorsTask.kt)

The Kotlin Compose task generates a file with all the necessary info, a sample of this can be seen
[here](test/resources/MockColors.expected.kotlin)

It needs 4 arguments:

- `packageName: String`: The package that the generated file will have at the top.
- `checkColorCorrectness: Boolean`: If there are more than one theme, it can check all themes has
  the same number and names of color tokens. If one is missing in one theme, it crashes indicating
  the missing one/s.
- `outputPath: String`: The path where the file will be generated
- a `varargs` of `ThemeFile`: the list of figma files associated to a specific theme by name.

Sample:

```kotlin
val kotlinComposeColorsTask: Task =
    KotlinComposeColorsTask(
        packageName = "com.stuart.shared.ui.design.system.core",
        checkColorCorrectness = true,
        outputPath = "some/path/to/module/with/Colors.kt",
        ThemeFile("light", FileKey("auAVu6zRJ39zECKv6hWDmH")),
        ThemeFile("dark", FileKey("wizOikAT1Wigtx6zHpfC87")),
    )
```
