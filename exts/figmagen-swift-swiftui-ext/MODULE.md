# Module figmagen-swift-swiftui-ext

## [SwiftUiColorsTask](/main/kotlin/com/stuart/figmagen/swift/swift/ui/extensions/SwiftUiColorsTask.kt)

The SwiftUI task generates a file with all the necessary info.

It needs 4 arguments:

- `checkColorCorrectness: Boolean`: If there are more than one theme, it can check all themes has
  the same number and names of color tokens. If one is missing in one theme, it crashes indicating
  the missing one/s.
- `outputPath: String`: The path where the file will be generated
- a `varargs` of `ThemeFile`: the list of figma files associated to a specific theme by name.

Sample:

```kotlin
val swiftUiColorsTask: Task =
    SwiftUiColorsTask(
        checkColorCorrectness = true,
        outputPath = "some/path/to/module/with/Colors.kt",
        ThemeFile("light", FileKey("auAVu6zRJ39zECKv6hWDmH")),
        ThemeFile("dark", FileKey("wizOikAT1Wigtx6zHpfC87")),
    )
```
