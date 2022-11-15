package com.stuart.figmagen.swift.swift.ui.extensions.internal

import java.util.Locale

internal enum class SwiftMutability {
    Let,
    Var,
    Empty;

    override fun toString(): String = if (this == Empty) "" else name.lowercase(Locale.getDefault())
}
