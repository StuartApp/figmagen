package com.stuart.figmagen.kotlin.compose.extensions.internal

internal data class KotlinClass(
    val annotations: MutableList<KotlinAnnotation> = mutableListOf(),
    var visibility: KotlinVisibility = KotlinVisibility.Public,
    var name: String,
    val valueArguments: MutableList<KotlinValueArgument> = mutableListOf(),
    val properties: MutableList<KotlinProperty> = mutableListOf(),
    val functions: MutableList<KotlinFunction> = mutableListOf(),
    val classes: MutableList<KotlinClass> = mutableListOf(),
) {
    override fun toString(): String = buildString {
        for (annotation in annotations) {
            appendLine("@$annotation")
        }
        appendLine("$visibility class $name" + if (valueArguments.isNotEmpty()) "(" else "")
        for (valueArgument in valueArguments) {
            appendLine("$valueArgument,".prependIndent())
        }
        if (valueArguments.isNotEmpty()) append(")")
        if ((properties + functions + classes).isNotEmpty()) {
            appendLine(" {")
            appendLine()
            for (property in properties) {
                "$property".indented().forEach(::appendLine)
            }
            for (function in functions) {
                "$function".indented().forEach(::appendLine)
            }

            for (kclass in classes) {
                "$kclass".indented().forEach(::appendLine)
            }
            appendLine("}")
        } else {
            appendLine()
        }
    }
}

internal fun buildKotlinClass(name: String, builder: KotlinClass.() -> Unit = {}): KotlinClass {
    val kclass = KotlinClass(name = name)
    builder(kclass)
    return kclass
}
