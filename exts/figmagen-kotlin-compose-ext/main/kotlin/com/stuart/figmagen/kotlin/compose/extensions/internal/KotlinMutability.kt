package com.stuart.figmagen.kotlin.compose.extensions.internal

import java.util.Locale

internal enum class KotlinMutability {
    Val,
    Var,
    VarArgs,
    Empty,
    ;

    override fun toString(): String = if (this == Empty) "" else name.lowercase(Locale.getDefault())
}
