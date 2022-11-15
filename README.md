[![MavenCentral](https://img.shields.io/maven-central/v/com.stuart.figmagen/figmagen-core?label=MavenCentral)](https://repo1.maven.org/maven2/com/stuart/figmagen/figmagen-core/)
[![Snapshot](https://img.shields.io/nexus/s/com.stuart.figmagen/figmagen-core?server=https%3A%2F%2Fs01.oss.sonatype.org%2F&label=Snapshot)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/stuart/figmagen/figmagen-core/)
[![Build](https://img.shields.io/github/workflow/status/StuartApp/figmagen/build-kotlin?label=Build&logo=GitHub)](https://github.com/StuartApp/figmagen/tree/main)

# Figmagen

Generate tokens from Figma easily for multiple languages or frameworks

## Download

```kotlin
dependencies {
    implementation("com.stuart.figmagen:figmagen-core:$version")

    // Kotlin Compose extensions
    implementation("com.stuart.figmagen:figmagen-kotlin-compose-ext:$version")

    // Swift SwiftUI extensions
    implementation("com.stuart.figmagen:figmagen-swift-swiftui-ext:$version")
}
```

## Usage

1. Create a new instance
   of [`Figmagen`](figmagen-core/main/kotlin/com/stuart/figmagen/Figmagen.kt):

    ```kotlin
    // figmaToken is optional as it can be provided
    // using `FIGMA_TOKEN` environment variable
    val figmagen = Figmagen(figmaToken = "...")
    ```

2. Add tasks to `Figmagen`:

    ```kotlin
    val swiftUiColorsTask: Task =
        SwiftUiColorsTask(
            checkColorCorrectness = true,
            outputPath = "some/path/to/module/with/Colors.kt",
            ThemeFile("light", FileKey("auAVu6zRJ39zECKv6hWDmH")),
            ThemeFile("dark", FileKey("wizOikAT1Wigtx6zHpfC87")),
        )
    
    figmagen.addTask(kotlinComposeColorsTask)
    ```

3. Generate all files from all tasks

```kotlin
figmagen.generate()
```

## Basic tasks

The basic tasks are generic tasks which fetch info from Figma files. They have to be extended to add
the functionality of each language or platform, for example `KotlinComposeColorsTask`
extends `ColorsTask` and it has default bindings to generate Kotlin classes, properties and so on.

There are default implementations for all tasks, but if one is missing, they can be extended easily
to adapt your needs. Feels free to ask for more tasks or contribute them :)

### [ColorsTask](figmagen-core/main/kotlin/com/stuart/figmagen/tasks/ColorsTask.kt)

After providing a list of theme files, it has a `getColors` function with return a list of all
colors of all themes provided.

#### Extending ColorsTask

- `themeFiles` has to be overridden providing them.
- `run` function has to be overridden and should return the list of `TaskFile` that will be
  generated.
- Use `getColors` function to easily provides a list of `TaskFile` to the `run` function.

## Implementation for basic tasks

### ColorTasks

- [Kotlin Compose Colors task](exts/figmagen-kotlin-compose-ext/main/kotlin/com/stuart/figmagen/kotlin/compose/extensions/KotlinComposeColorsTask.kt)
- [SwiftUiColorsTask](exts/figmagen-swift-swiftui-ext/main/kotlin/com/stuart/figmagen/swift/swift/ui/extensions/SwiftUiColorsTask.kt)
