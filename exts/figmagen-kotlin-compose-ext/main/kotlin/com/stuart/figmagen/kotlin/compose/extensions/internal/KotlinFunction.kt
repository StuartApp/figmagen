package com.stuart.figmagen.kotlin.compose.extensions.internal

internal data class KotlinFunction(
    val annotations: MutableList<KotlinAnnotation> = mutableListOf(),
    var visibility: KotlinVisibility = KotlinVisibility.Public,
    var name: String,
    val valueArguments: MutableList<KotlinValueArgument> = mutableListOf(),
    var returnType: String? = null,
    var body: String? = null,
) {
    override fun toString(): String = buildString {
        val funName = name.sanitizeKotlinKeyword()
        for (annotation in annotations) {
            appendLine("@$annotation")
        }
        val returningType = if (returnType != null) ": $returnType" else ""
        if (valueArguments.isEmpty()) {
            appendLine("$visibility fun $funName()$returningType {")
        } else {
            appendLine("$visibility fun $funName(")
            for (valueArgument in valueArguments) {
                val sanitizedValueArgument =
                    valueArgument.apply { mutability = KotlinMutability.Empty }
                appendLine("$sanitizedValueArgument,".prependIndent())
            }
            appendLine(")$returningType {")
        }
        body?.indented()?.dropLastWhile(String::isBlank)?.forEach(::appendLine)
        appendLine("}")
    }
}

internal fun buildKotlinFunction(
    name: String,
    builder: KotlinFunction.() -> Unit = {}
): KotlinFunction {
    val function = KotlinFunction(name = name)
    builder(function)
    return function
}
