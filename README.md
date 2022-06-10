# Figmagen

Generate tokens from Figma easily for multiple languages or frameworks

## Usage

1. Create a new instance
   of [`Figmagen`](figmagen-core/src/main/kotlin/com/stuart/figmagen/Figmagen.kt):

```kotlin
// figmaToken is optional as it can be provided
// using `FIGMA_TOKEN` environment variable
val figmagen = Figmagen(figmaToken = "...")
```

2. Add tasks to `Figmagen`:

```kotlin

val kotlinComposeColorsTask: Task =
    KotlinComposeColorsTask(
        packageName = "com.stuart.shared.ui.design.system.core",
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

### [ColorsTask](figmagen-core/src/main/kotlin/com/stuart/figmagen/tasks/ColorsTask.kt)

After providing a list of theme files, it has a `getColors` function with return a list of all
colors of all themes provided.

#### Extending ColorsTask

- `themeFiles` has to be overridden providing them.
- `run` function has to be overridden and should return the list of `TaskFile` that will be
  generated.
- Use `getColors` function to easily provides a list of `TaskFile` to the `run` function.

## Implementation for basic tasks

### ColorTasks

- [Kotlin Compose Colors task](exts/figmagen-kotlin-compose-ext/src/main/kotlin/com/stuart/figmagen/kotlin/compose/extensions/KotlinComposeColorsTask.kt)
