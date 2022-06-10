package com.stuart.figmagen.internal

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

internal val client = OkHttpClient()

internal val baseUrl: HttpUrl =
    HttpUrl.Builder()
        .scheme("https")
        .host("api.figma.com")
        .addPathSegment("v1")
        .addPathSegment("files")
        .build()

internal val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

internal fun baseRequest(figmaToken: String?): Request.Builder =
    Request.Builder().addHeader("X-FIGMA-TOKEN", checkNotNull(figmaToken))

internal val fileJsonAdapter: JsonAdapter<FigmaApi.File> = moshi.adapter(FigmaApi.File::class.java)

internal val styleWrapperJsonAdapter: JsonAdapter<FigmaApi.StyleWrapper> =
    moshi.adapter(FigmaApi.StyleWrapper::class.java)
