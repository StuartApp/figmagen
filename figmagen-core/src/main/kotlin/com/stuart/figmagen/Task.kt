package com.stuart.figmagen

import com.stuart.figmagen.models.TaskFile

/**
 * A task is simply a function with returns a list of `TaskFile` that `Figmagen` will generate.
 *
 * There are default implementations for multiple styles or components like colors or typographies.
 */
public abstract class Task {

    public lateinit var figmaToken: String

    public abstract suspend fun run(): List<TaskFile>
}
