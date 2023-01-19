package com.stuart.figmagen.internal

import com.squareup.moshi.Json

internal object FigmaApi {

    data class Data<T>(val data: T)

    data class StyleWrapper(val meta: Meta)

    data class Meta(val styles: List<Style>)

    data class Style(
        val key: String,
        @Json(name = "node_id") val nodeId: String,
        @Json(name = "style_type") val styleType: Type,
    ) {
        val isFill: Boolean
            get() = styleType == Type.FILL

        enum class Type {
            FILL,
            EFFECT,
            GRID,
            TEXT,
        }
    }

    data class File(val name: String, val nodes: Map<String, Node>)

    data class Node(val document: Document)

    data class Document(
        val id: String,
        val name: String,
        val fills: List<Fill>,
        val effects: List<Effect>,
    )

    data class Fill(val color: Color?)

    data class Effect(val type: String?)

    data class Color(val r: Float, val g: Float, val b: Float, val a: Float)
}

internal fun List<FigmaApi.Style>.nodeIdsAsQueryParameter(): String =
    joinToString(",", transform = FigmaApi.Style::nodeId)
