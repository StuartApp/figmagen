package com.stuart.figmagen.test.fakes

import com.stuart.figmagen.models.Color
import com.stuart.figmagen.models.ColorFile
import com.stuart.figmagen.tree.TreeNode
import com.stuart.figmagen.tree.node
import com.stuart.figmagen.tree.tree

public fun fakeColorFileTree(
    builder: (TreeNode<ColorFile>.() -> TreeNode<ColorFile>)? = null,
): TreeNode<ColorFile> = tree(ColorFile.Root(), builder)

public fun TreeNode<ColorFile>.fakeDirectory(
    name: String,
    builder: (TreeNode<ColorFile>.() -> TreeNode<ColorFile>)? = null,
): TreeNode<ColorFile> = node(value.colorFileDirectory(name), builder)

public fun TreeNode<ColorFile>.fakeColor(color: Color): TreeNode<ColorFile> =
    node(value.colorFileColor(color))
