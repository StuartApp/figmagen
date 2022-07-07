package com.stuart.figmagen.tree

public data class TreeNode<T>
private constructor(
    public val value: T,
    private val _children: MutableList<TreeNode<T>> = mutableListOf()
) : Iterable<TreeNode<T>> {

    public constructor(value: T) : this(value, mutableListOf())

    private var _parent: TreeNode<T>? = null
    /** The converse notion of a child, an immediate ancestor. */
    public val parent: TreeNode<T>?
        get() = _parent

    /** A group of nodes with the same parent. */
    public val children: List<TreeNode<T>>
        get() = _children

    public val root: TreeNode<T>
        get() = findRoot()

    /**
     * Add new child to current node or root.
     *
     * @param child A node which will be directly connected to current node.
     */
    public fun addChild(child: T) {
        child(child)
    }

    /**
     * Add new child to current node or root.
     *
     * @param child A node which will be directly connected to current node.
     *
     * @return the new child
     */
    public fun child(child: T): TreeNode<T> {
        val newChild = TreeNode(child)
        newChild._parent = this
        _children.add(newChild)
        return newChild
    }

    /**
     * Removes a single instance of the specified node from this tree, if it is present.
     *
     * @return `true` if the node has been successfully removed; `false` if it was not present in
     * the tree.
     */
    public fun removeChild(child: TreeNode<T>): Boolean {
        println(child.value)
        val removed = child._parent?._children?.remove(child)
        child._parent = null
        return removed ?: false
    }

    /**
     * This function go through tree and counts children. Root element is not counted.
     * @return All child and nested child count.
     */
    public fun nodeCount(): Int {
        if (_children.isEmpty()) return 0
        return _children.size + _children.sumOf { it.nodeCount() }
    }

    /**
     * @return The number of edges on the longest path between current node and a descendant leaf.
     */
    public fun height(): Int {
        val childrenMaxDepth = _children.maxOfOrNull(TreeNode<T>::height) ?: -1
        return childrenMaxDepth + 1
    }

    /**
     * Distance is the number of edges along the shortest path between two nodes.
     * @return The distance between current node and the root.
     */
    public fun depth(): Int {
        var depth = 0
        var tempParent = parent

        while (tempParent != null) {
            depth++
            tempParent = tempParent.parent
        }
        return depth
    }

    /** Remove all children from root and every node in tree. */
    public fun clear() {
        _parent = null
        _children.forEach { it.clear() }
        _children.clear()
    }

    override fun toString(): String {
        return value.toString()
    }

    public fun prettyString(onPrint: ((T) -> String)? = null): String {
        val stringBuilder = StringBuilder()
        print(stringBuilder, "", "", onPrint)
        return stringBuilder.toString()
    }

    private fun print(
        stringBuilder: StringBuilder,
        prefix: String,
        childrenPrefix: String,
        onPrint: ((T) -> String)?,
    ) {
        stringBuilder.append(prefix)
        stringBuilder.append(onPrint?.invoke(value) ?: value)
        stringBuilder.append('\n')
        val childIterator = _children.iterator()
        while (childIterator.hasNext()) {
            val node = childIterator.next()
            if (childIterator.hasNext()) {
                node.print(stringBuilder, "$childrenPrefix├── ", "$childrenPrefix│   ", onPrint)
            } else {
                node.print(stringBuilder, "$childrenPrefix└── ", "$childrenPrefix    ", onPrint)
            }
        }
    }

    /**
     * Tree is iterated by using `Pre-order Traversal Algorithm"
     * 1. Check if the current node is empty or null.
     * 2. Display the data part of the root (or current node).
     * 3. Traverse the left subtree by recursively calling the pre-order function.
     * 4. Traverse the right subtree by recursively calling the pre-order function.
     *
     * ```markdown
     *                  colors
     *                  / |   \
     *                 /  |     \
     *               a    b       c
     *              /  \       /  |  \
     *            a1    a2    c1 c2  c3
     *           /    / |  \
     *         a11  a21 a22 a23
     *
     * Output: colors a a1 a11 a2 a21 a22 a23 b c c1 c2 c3
     * ```
     */
    override fun iterator(): Iterator<TreeNode<T>> = PreOrderTreeIterator(this)
}

private fun <T> TreeNode<T>.findRoot(): TreeNode<T> = parent?.findRoot<T>() ?: this
