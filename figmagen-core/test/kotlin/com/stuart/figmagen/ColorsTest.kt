package com.stuart.figmagen

import com.stuart.figmagen.fakes.samples.dummyLightColors
import com.stuart.figmagen.fakes.samples.expectedDummyLight
import com.stuart.figmagen.fakes.samples.expectedSampleOne
import com.stuart.figmagen.fakes.samples.fakesSampleOneColors
import com.stuart.figmagen.models.toTree
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ColorsTest {

    @Test
    fun `toTree function transform correctly a list of color to a TreeNode of ColorFile`() {
        assertEquals(expected = expectedSampleOne, actual = fakesSampleOneColors.toTree())
    }

    @Test
    fun `dummy light colors toTree`() {
        assertEquals(expected = expectedDummyLight, actual = dummyLightColors.toTree())
    }
}
