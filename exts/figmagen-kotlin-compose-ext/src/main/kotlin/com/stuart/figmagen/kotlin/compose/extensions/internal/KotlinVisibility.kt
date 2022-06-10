package com.stuart.figmagen.kotlin.compose.extensions.internal

import java.util.Locale

internal enum class KotlinVisibility {
    Internal,
    Public,
    Private,
    ;

    override fun toString(): String = name.lowercase(Locale.getDefault())
}