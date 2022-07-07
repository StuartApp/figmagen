package com.stuart.figmagen.models

/** List of tokens */
internal enum class TokenType(internal val type: String) {
    Color("color"),
    Typography("typography"),
    Elevation("elevation"),
    Unknown("unknown")
}

internal val List<String>.isColor: Boolean
    get() = firstOrNull() == TokenType.Color.type

internal val List<String>.isTypography: Boolean
    get() = firstOrNull() == TokenType.Typography.type

internal val List<String>.isElevation: Boolean
    get() = firstOrNull() == TokenType.Elevation.type

internal val List<String>.isUnknown: Boolean
    get() = firstOrNull() == TokenType.Unknown.type
