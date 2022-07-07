package com.stuart.figmagen.kotlin.compose.extensions.internal

internal data class KotlinAnnotation(val name: String) {
    override fun toString(): String = name
}

internal fun buildKotlinAnnotation(name: String) = KotlinAnnotation(name)
