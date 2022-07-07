package com.stuart.figmagen.tree

import java.util.Stack

internal class PreOrderTreeIterator<T>(root: TreeNode<T>) : Iterator<TreeNode<T>> {

    private val stack = Stack<TreeNode<T>>()

    init {
        stack.push(root)
    }

    override fun hasNext(): Boolean = stack.isNotEmpty()

    override fun next(): TreeNode<T> {
        val node = stack.pop()
        node.children.asReversed().forEach { stack.push(it) }
        return node
    }
}
