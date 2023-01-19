package com.stuart.figmagen.models

internal val String.isColor: Boolean
    get() = this.firstOrNull()?.isLetter() == true
