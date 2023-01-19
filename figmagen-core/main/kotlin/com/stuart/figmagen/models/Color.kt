package com.stuart.figmagen.models

import com.stuart.figmagen.tree.TreeNode

/**
 * @property path Full path including all groups of the color -> `customer/primary/main`
 * @property theme The theme which the color belongs -> `light`
 * @property rgba RGBA color values
 * @property name The color name, should be the same that the last group -> `main`
 * @property directories is the list of all color important directories -> `(customer/primary)`
 * @property nameAsColorFile transform the `Color` in `ColorFile.Color`.
 * @property pathAsList the path split by `/` so it returns `List<String>` instead of `String`.
 *
 * A valid color must have a `path` with at least two files, one directory, and the color, examples:
 * - `primary/main`
 * - `primary/contrast`
 * - `surface/primary/main`
 * - `surface/primary/contrast`
 */
public data class Color(
    val path: String,
    val theme: String,
    val rgba: RGBA,
) {
    val name: String = path.split("/").last()

    public val directories: List<String> = path.split("/").dropLast(1)

    public val nameAsColorFile: ColorFile.Color = ColorFile.Color(this)

    public val pathAsList: List<String> = path.split("/")

    public data class RGBA(val red: Float, val green: Float, val blue: Float, val alpha: Float)
}

/**
 * Transform a list of `Color` into a tree of `ColorFile`.
 *
 * ```markdown
 * colors
 * ├── primary
 * │   ├── main
 * │   ├── soft
 * │   └── contrast
 * │       ├── main
 * │       └── soft
 * └── secondary
 *     ├── main
 *     └── soft
 * ```
 */
public fun List<Color>.toTree(): TreeNode<ColorFile> {
    val treeNode: TreeNode<ColorFile> = TreeNode(ColorFile.Root())

    for (color in this) {
        treeNode.add(
            remainingPath = color.directories.joinToString("/"),
            originalPath = color.directories.joinToString("/"),
            color = color,
        )
    }

    return treeNode
}

private fun TreeNode<ColorFile>.add(
    remainingPath: String,
    originalPath: String,
    color: Color,
) {
    val directory = remainingPath.substringBefore("/").takeIf { it.isNotBlank() }
    val parentPath = originalPath.substringBefore(remainingPath).dropLastWhile { it == '/' }
    val parentPathAsList = parentPath.split("/").filter { it.isNotBlank() }
    val nextRemainingPath =
        remainingPath.takeIf { it.contains("/") }?.substringAfter("/")?.dropWhile { it == '/' }
    if (directory != null) {
        val dirNode = ColorFile.Directory(directory, parentPathAsList)
        val newOrFoundNode = findOrAddColorFile(dirNode)
        if (nextRemainingPath != null) {
            newOrFoundNode.add(nextRemainingPath, originalPath, color)
        } else newOrFoundNode.child(ColorFile.Color(color))
    }
}

private fun TreeNode<ColorFile>.findOrAddColorFile(
    dirNode: ColorFile.Directory,
): TreeNode<ColorFile> =
    root.firstOrNull { (dirNode == it.value) || (dirNode.isInPreviousColorFileParents(it.value)) }
        ?: child(dirNode)

private fun ColorFile.isInPreviousColorFileParents(other: ColorFile): Boolean =
    runCatching {
            val parentDirName = parentDirectories.last()
            other.name == parentDirName && other.parentDirectories == parentDirectories
        }
        .getOrElse { false }
