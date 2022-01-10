@file:DependsOn("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
@file:DependsOn("com.squareup.moshi:moshi:1.13.0")
@file:DependsOn("com.squareup.moshi:moshi-kotlin:1.13.0")
@file:DependsOn("com.squareup.okhttp3:okhttp:4.9.3")
@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalStdlibApi::class)
@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException

/**
 * How to run:
 *
 * ```kotlin
 * kotlin figmagen.main.kts language=[language] token=[token]
 * ```
 *
 * Example:
 *
 * ```kotlin
 * kotlin figmagen.main.kts language=swift token=292559-ac1725a4-0f54-43e1-9421-9eaa09213859
 * ```
 */
val language: String =
    checkNotNull(args.firstOrNull()?.substringAfter("=")) { "Language is missing" }
val token: String = checkNotNull(args.getOrNull(1)?.substringAfter("=")) { "Token is missing" }

val client = OkHttpClient()

val baseUrl: HttpUrl =
    HttpUrl.Builder()
        .scheme("https")
        .host("api.figma.com")
        .addPathSegment("v1")
        .addPathSegment("files")
        .build()
val baseRequest: Request.Builder = Request.Builder().addHeader("X-FIGMA-TOKEN", token)

val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

runBlocking {
    val lightColorsFileKey = "2q2V7H3JXbohe14zNPRnWU"
    val darkColorsFileKey = "Lyk5Tk6tIRoi8ojq9EHZKo"

    val lightColorsNodeIds = async {
        getStyles(lightColorsFileKey).meta.styles.map(Figma.Style::node_id)
    }
    val darkColorsNodeIds = async {
        getStyles(darkColorsFileKey).meta.styles.map(Figma.Style::node_id)
    }

    val lightColors = async { getColors(lightColorsFileKey, lightColorsNodeIds.await()) }
    val darkColors = async { getColors(darkColorsFileKey, darkColorsNodeIds.await()) }

    when (language) {
        "${Language.Kotlin}" -> TODO("Language not implemented")
        "${Language.Swift}" -> {
            generateSwiftColors(getColorsMap(lightColors.await(), darkColors.await()))
        }
        else -> error("Unknown language: $language")
    }
}

suspend fun getStyles(fileKey: String): Figma.StyleWrapper {
    println("Getting styles for file `$fileKey`")

    val styleWrapperJsonAdapter: JsonAdapter<Figma.StyleWrapper> =
        moshi.adapter(Figma.StyleWrapper::class.java)

    val request: Request =
        baseRequest
            .url(baseUrl.newBuilder().addPathSegment(fileKey).addPathSegment("styles").build())
            .build()

    val response = client.newCall(request).await()

    val content: String? = response.body?.string()

    if (response.code == 200 && content != null) return styleWrapperJsonAdapter.fromJson(content)!!
    else error("There is an error: Status code: ${response.code}, body: ${response.body}")
}

suspend fun getColors(fileKey: String, nodeIds: List<String>): List<KeirinColor> {
    println("Getting colors for fileKey `$fileKey`")

    val fileJsonAdapter: JsonAdapter<Figma.File> = moshi.adapter(Figma.File::class.java)

    val request: Request =
        baseRequest
            .url(
                baseUrl
                    .newBuilder()
                    .addPathSegment(fileKey)
                    .addPathSegment("nodes")
                    .addQueryParameter("ids", nodeIds.joinToString(","))
                    .build()
            )
            .build()

    val response = client.newCall(request).await()
    val content: String? = response.body?.string()

    if (response.code == 200 && content != null) {
        val data: Figma.File = fileJsonAdapter.fromJson(content)!!

        return data.nodes
            .filter {
                val name = it.value.document.name
                name.startsWith("krnLight") || name.startsWith("krnDark")
            }
            .map { nodes ->
                val name = nodes.value.document.name
                val color =
                    checkNotNull(nodes.value.document.fills.firstOrNull()?.color) {
                        "The node with name `$name` must have a color"
                    }
                KeirinColor(name, color.r, color.g, color.b, color.a)
            }
    } else error("There is an error: Status code: ${response.code}, body: ${response.body}")
}

fun getColorsMap(
    lightColors: List<KeirinColor>,
    darkColors: List<KeirinColor>
): Map<String, List<KeirinColor>> {
    fun sanitizeKeirinColorName(keirinColor: KeirinColor) = keirinColor.name.substringAfter("/")

    val lightColorNames = lightColors.map(::sanitizeKeirinColorName).toSortedSet()
    val darkColorNames = darkColors.map(::sanitizeKeirinColorName).toSortedSet()

    check(lightColorNames == darkColorNames) {
        "Light and dark colors are not matching, cry to the designers"
    }

    return (lightColors + darkColors).groupBy { it.name.substringAfter("/") }
}

fun generateSwiftColors(colors: Map<String, List<KeirinColor>>) {
    val tempDir = File("colors/ios/generated/") // TODO: Delete
    val iosDir = "$tempDir/Stuart/Stuart/Shared/UI/Core/KeirinColors/Sources/KeirinColors"
    val iosAssetsDir =
        File("$iosDir/Colors.xcassets/").apply {
            deleteRecursively()
            mkdirs()
        }

    for (color in colors) {
        val (lightColor, darkColor) = color.value
        val fileName =
            "${lightColor.name.replace("/", "-").substringAfter("-")}.colorset/Contents.json"

        File("$iosAssetsDir/$fileName").apply {
            parentFile.mkdirs()
            createNewFile()

            val fileContent =
                """
                    |{
                    |  "colors" : [
                    |    {
                    |      "color" : {
                    |        "color-space" : "srgb",
                    |        "components" : {
                    |          "alpha" : "${lightColor.alpha}",
                    |          "blue" : "${lightColor.blue}",
                    |          "green" : "${lightColor.green}",
                    |          "red" : "${lightColor.red}"
                    |        }
                    |      },
                    |      "idiom" : "universal"
                    |    },
                    |    {
                    |      "appearances" : [
                    |        {
                    |          "appearance" : "luminosity",
                    |          "value" : "dark"
                    |        }
                    |      ],
                    |      "color" : {
                    |        "color-space" : "srgb",
                    |        "components" : {
                    |          "alpha" : "${darkColor.alpha}",
                    |          "blue" : "${darkColor.blue}",
                    |          "green" : "${darkColor.green}",
                    |          "red" : "${darkColor.red}"
                    |        }
                    |      },
                    |      "idiom" : "universal"
                    |    }
                    |  ],
                    |  "info" : {
                    |    "author" : "xcode",
                    |    "version" : 1
                    |  }
                    |}
                """.trimMargin()
            writeText(fileContent)
        }
    }

    File("$iosDir/KeirinColors.swift").apply {
        parentFile.mkdirs()
        createNewFile()

        val swiftFileContent =
            buildList {
                    add("import SwiftUI")
                    add("")
                    add("@available(iOS 13.0, *)")
                    add("public enum KeirinColors {")
                    colors.forEach { color ->
                        val fileName =
                            color.value.first().name.replace("/", "-").substringAfter("-")
                        val variableName = fileName.dashToCamelCase()

                        val colorLine =
                            """public static let $variableName = Color.init("$fileName")"""
                        add(colorLine.prependIndent("    "))
                    }
                    add("}")
                }
                .joinToString("\n")

        writeText(swiftFileContent)
    }
    println("iOS colors generated")
}

enum class Language {
    Kotlin,
    Swift;

    override fun toString(): String = name.lowercase()
}

data class KeirinColor(
    val name: String,
    val red: Double,
    val green: Double,
    val blue: Double,
    val alpha: Double
)

object Figma {

    data class Data<T>(val data: T)

    data class StyleWrapper(val meta: Meta)

    data class Meta(val styles: List<Style>)

    data class Style(val key: String, val node_id: String)

    data class File(val name: String, val nodes: Map<String, Node>)

    data class Node(val document: Document)

    data class Document(val id: String, val name: String, val fills: List<Fill>)

    data class Fill(val color: Color?)

    data class Color(val r: Double, val g: Double, val b: Double, val a: Double)
}

suspend fun Call.await(): Response = suspendCancellableCoroutine { continuation ->
    enqueue(
        object : Callback {
            override fun onResponse(call: Call, response: Response) {
                continuation.resume(response, null)
            }

            override fun onFailure(call: Call, e: IOException) {
                if (continuation.isCancelled) return
                continuation.resumeWithException(e)
            }
        }
    )

    continuation.invokeOnCancellation { runCatching { cancel() } }
}

fun String.dashToCamelCase(): String =
    split("").reduce { acc, c ->
        if (acc.lastOrNull() == '-') "${acc.dropLast(1)}${c.uppercase()}" else "$acc$c"
    }
