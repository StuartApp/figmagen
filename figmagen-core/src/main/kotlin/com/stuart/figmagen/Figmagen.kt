package com.stuart.figmagen

import com.stuart.figmagen.internal.getFigmaTokenFromEnv
import java.io.File

/**
 * Figmagen uses a list of provided tasks and executes them. The default instance can be created so:
 *
 * ```kotlin
 * val figmagen = Figmagen(figmaToken = "...")
 *
 * figmagen.addTask(...)
 *
 * figmagen.generate()
 * ```
 *
 * The token is optional as it can be obtained from an environment variable called `FIGMA_TOKEN`.
 *
 * `Figmagen::generate` function is `suspend`, it is necessary to use Kotlin Coroutines to run it.
 */
public abstract class Figmagen internal constructor(internal val figmaToken: String) {

    private val tasks: MutableList<Task> = mutableListOf()

    public fun addTask(task: Task) {
        tasks.add(task)
    }

    public fun removeTask(task: Task) {
        tasks.remove(task)
    }

    public fun removeTask(predicate: (Task) -> Boolean) {
        tasks.removeIf(predicate)
    }

    public suspend fun generate() {
        tasks
            .onEach { it.figmaToken = figmaToken }
            .flatMap { task -> task.run() }
            .forEach { file ->
                File(file.path).apply {
                    parentFile.mkdirs()
                    createNewFile()
                    writeText(file.content)
                }
            }
    }
}

public fun Figmagen(figmaToken: String? = getFigmaTokenFromEnv()): Figmagen {

    checkNotNull(figmaToken) { "The Figma token must not be null" }

    return object : Figmagen(figmaToken) {}
}
