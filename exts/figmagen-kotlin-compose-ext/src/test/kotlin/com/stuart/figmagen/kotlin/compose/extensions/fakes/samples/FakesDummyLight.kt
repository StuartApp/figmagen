package com.stuart.figmagen.kotlin.compose.extensions.fakes.samples

import com.stuart.figmagen.models.Color
import com.stuart.figmagen.models.Color.RGBA
import com.stuart.figmagen.models.ColorFile
import com.stuart.figmagen.test.fakes.fakeColor
import com.stuart.figmagen.test.fakes.fakeColorFileTree
import com.stuart.figmagen.test.fakes.fakeDirectory
import com.stuart.figmagen.tree.TreeNode

internal val Color_Primary_Main =
    Color(
        path = "color/primary/main",
        theme = "light",
        rgba = RGBA(0.0f, 0.36f, 1.0f, 1.0f),
    )

internal val Color_Primary_Soft =
    Color(
        path = "color/primary/soft",
        theme = "light",
        rgba = RGBA(0.8f, 0.87f, 1.0f, 1.0f),
    )

internal val Color_Primary_Contrast_Main =
    Color(
        path = "color/primary/contrast/main",
        theme = "light",
        rgba = RGBA(1.0f, 1.0f, 1.0f, 1.0f),
    )

internal val Color_Primary_Contrast_High =
    Color(
        path = "color/primary/contrast/high",
        theme = "light",
        rgba = RGBA(0.0f, 1.0f, 0.0f, 1.0f),
    )

internal val Color_Secondary_Main =
    Color(
        path = "color/secondary/main",
        theme = "light",
        rgba = RGBA(0.23f, 0.81f, 0.67f, 1.0f),
    )

internal val Color_Secondary_Soft =
    Color(
        path = "color/secondary/soft",
        theme = "light",
        rgba = RGBA(0.83f, 0.94f, 0.93f, 1.0f),
    )

internal val Color_Tertiary_Main =
    Color(
        path = "color/tertiary/main",
        theme = "light",
        rgba = RGBA(1.0f, 0.75f, 0.18f, 1.0f),
    )

internal val Color_Tertiary_Soft =
    Color(
        path = "color/tertiary/soft",
        theme = "light",
        rgba = RGBA(1.0f, 0.98f, 0.91f, 1.0f),
    )

internal val Color_Surface_Main =
    Color(
        path = "color/surface/main",
        theme = "light",
        rgba = RGBA(1.0f, 1.0f, 1.0f, 1.0f),
    )

internal val Color_Surface_Soft =
    Color(
        path = "color/surface/soft",
        theme = "light",
        rgba = RGBA(0.93f, 0.97f, 1.0f, 1.0f),
    )

internal val Color_Surface_Contrast_High =
    Color(
        path = "color/surface/contrast/high",
        theme = "light",
        rgba = RGBA(0.5f, 0.0f, 0.5f, 1.0f),
    )

internal val Color_Surface_Contrast_Main =
    Color(
        path = "color/surface/contrast/main",
        theme = "light",
        rgba = RGBA(0.22f, 0.25f, 0.32f, 1.0f),
    )

internal val Color_Disabled_Main =
    Color(
        path = "color/disabled/main",
        theme = "light",
        rgba = RGBA(0.59f, 0.64f, 0.69f, 1.0f),
    )

internal val Color_Disabled_Soft =
    Color(
        path = "color/disabled/soft",
        theme = "light",
        rgba = RGBA(0.82f, 0.84f, 0.86f, 1.0f),
    )

val dummyLightColors =
    listOf(
        Color_Primary_Main,
        Color_Primary_Soft,
        Color_Primary_Contrast_Main,
        Color_Primary_Contrast_High,
        Color_Secondary_Main,
        Color_Secondary_Soft,
        Color_Tertiary_Main,
        Color_Tertiary_Soft,
        Color_Surface_Main,
        Color_Surface_Soft,
        Color_Surface_Contrast_High,
        Color_Surface_Contrast_Main,
        Color_Disabled_Main,
        Color_Disabled_Soft,
    )

internal val expectedDummyLight: TreeNode<ColorFile> = fakeColorFileTree {
    fakeDirectory("primary") {
        fakeColor(Color_Primary_Main)
        fakeColor(Color_Primary_Soft)
        fakeDirectory("contrast") {
            fakeColor(Color_Primary_Contrast_Main)
            fakeColor(Color_Primary_Contrast_High)
        }
    }

    fakeDirectory("secondary") {
        fakeColor(Color_Secondary_Main)
        fakeColor(Color_Secondary_Soft)
    }

    fakeDirectory("tertiary") {
        fakeColor(Color_Tertiary_Main)
        fakeColor(Color_Tertiary_Soft)
    }

    fakeDirectory("surface") {
        fakeColor(Color_Surface_Main)
        fakeColor(Color_Surface_Soft)
        fakeDirectory("contrast") {
            fakeColor(Color_Surface_Contrast_High)
            fakeColor(Color_Surface_Contrast_Main)
        }
    }

    fakeDirectory("disabled") {
        fakeColor(Color_Disabled_Main)
        fakeColor(Color_Disabled_Soft)
    }
}
