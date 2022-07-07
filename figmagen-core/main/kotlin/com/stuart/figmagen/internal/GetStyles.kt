package com.stuart.figmagen.internal

import okhttp3.Request

internal suspend fun getStyles(fileKey: String, token: String): List<FigmaApi.Style> {
    val request: Request =
        baseRequest(token)
            .url(baseUrl.newBuilder().addPathSegment(fileKey).addPathSegment("styles").build())
            .build()

    val response = client.newCall(request).await()

    val content: String =
        checkNotNull(response.body?.string()) {
            "The request body for getting the Figma styles must not be null, check the file provided"
        }

    return if (response.code == 200) {
        val figmaStyleWrapper: FigmaApi.StyleWrapper =
            checkNotNull(styleWrapperJsonAdapter.fromJson(content)) {
                "There were an issue parsing the content to `FigmaApi.StyleWrapper`"
            }
        figmaStyleWrapper.meta.styles
    } else error("There is an error: Status code: ${response.code}, body: $content")
}
