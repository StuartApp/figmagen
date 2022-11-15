package com.stuart.figmagen.swift.swift.ui.extensions.internal

import java.util.Locale

internal enum class SwiftVisibility {
    Public,
    Private;

    override fun toString(): String = name.lowercase(Locale.getDefault())
}
