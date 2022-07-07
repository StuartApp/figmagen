package com.stuart.figmagen.tree

public fun <T> tree(
    root: T,
    builder: (TreeNode<T>.() -> TreeNode<T>)? = null,
): TreeNode<T> {
    val tree = TreeNode<T>(root)
    builder?.invoke(tree)
    return tree
}

public fun <T> TreeNode<T>.node(
    value: T,
    builder: (TreeNode<T>.() -> TreeNode<T>)? = null,
): TreeNode<T> {
    val node = child(value)
    builder?.invoke(node)
    return node
}
