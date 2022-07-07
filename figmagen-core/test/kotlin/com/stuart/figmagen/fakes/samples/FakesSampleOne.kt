package com.stuart.figmagen.fakes.samples

import com.stuart.figmagen.models.Color
import com.stuart.figmagen.models.Color.RGBA
import com.stuart.figmagen.models.ColorFile
import com.stuart.figmagen.test.fakes.fakeColor
import com.stuart.figmagen.test.fakes.fakeColorFileTree
import com.stuart.figmagen.test.fakes.fakeDirectory
import com.stuart.figmagen.tree.TreeNode

internal val Group1_Subgroup1_one_Color =
    Color(
        path = "color/group1/subgroup1/one",
        theme = "light",
        rgba = RGBA(0F, 0F, 0F, 0F),
    )

internal val Group1_Subgroup1_two_Color =
    Color(
        path = "color/group1/subgroup1/two",
        theme = "light",
        rgba = RGBA(0F, 0F, 0F, 0F),
    )

internal val Group2_Subgroup1_one_Color =
    Color(
        path = "color/group2/subgroup1/one",
        theme = "light",
        rgba = RGBA(0F, 0F, 0F, 0F),
    )

internal val Group1_Subgroup2_one_Color =
    Color(
        path = "color/group1/subgroup2/one",
        theme = "light",
        rgba = RGBA(0F, 0F, 0F, 0F),
    )

internal val Group2_Subgroup1_two_Color =
    Color(
        path = "color/group2/subgroup1/two",
        theme = "light",
        rgba = RGBA(0F, 0F, 0F, 0F),
    )

internal val Group2_Subgroup2_one_Color =
    Color(
        path = "color/group2/subgroup2/one",
        theme = "light",
        rgba = RGBA(0F, 0F, 0F, 0F),
    )

internal val Group1_Subgroup1_AnotherGroup1_one_Color =
    Color(
        path = "color/group1/subgroup1/anothergroup1/one",
        theme = "light",
        rgba = RGBA(0F, 0F, 0F, 0F),
    )

internal val Group1_Subgroup1_AnotherGroup1_two_Color =
    Color(
        path = "color/group1/subgroup1/anothergroup1/two",
        theme = "light",
        rgba = RGBA(0F, 0F, 0F, 0F),
    )

val fakesSampleOneColors =
    listOf(
        Group1_Subgroup1_one_Color,
        Group1_Subgroup1_two_Color,
        Group1_Subgroup1_AnotherGroup1_one_Color,
        Group1_Subgroup1_AnotherGroup1_two_Color,
        Group1_Subgroup2_one_Color,
        Group2_Subgroup1_one_Color,
        Group2_Subgroup1_two_Color,
        Group2_Subgroup2_one_Color,
    )

internal val expectedSampleOne: TreeNode<ColorFile> = fakeColorFileTree {
    fakeDirectory("group1") {
        fakeDirectory("subgroup1") {
            fakeColor(Group1_Subgroup1_one_Color)
            fakeColor(Group1_Subgroup1_two_Color)
            fakeDirectory("anothergroup1") {
                fakeColor(Group1_Subgroup1_AnotherGroup1_one_Color)
                fakeColor(Group1_Subgroup1_AnotherGroup1_two_Color)
            }
        }
        fakeDirectory("subgroup2") { fakeColor(Group1_Subgroup2_one_Color) }
    }
    fakeDirectory("group2") {
        fakeDirectory("subgroup1") {
            fakeColor(Group2_Subgroup1_one_Color)
            fakeColor(Group2_Subgroup1_two_Color)
        }
        fakeDirectory("subgroup2") { fakeColor(Group2_Subgroup2_one_Color) }
    }
}
