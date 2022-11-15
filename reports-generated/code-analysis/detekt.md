# detekt

## Metrics

* 255 number of properties

* 109 number of functions

* 61 number of classes

* 14 number of packages

* 56 number of kt files

## Complexity Report

* 2,710 lines of code (loc)

* 2,117 source lines of code (sloc)

* 1,535 logical lines of code (lloc)

* 192 comment lines of code (cloc)

* 255 cyclomatic complexity (mcc)

* 204 cognitive complexity

* 10 number of total code smells

* 9% comment source ratio

* 166 mcc per 1,000 lloc

* 6 code smells per 1,000 lloc

## Findings (10)

### complexity, TooManyFunctions (2)

Too many functions inside a/an file/class/object/interface always indicate a violation of the single responsibility principle. Maybe the file/class/object/interface wants to manage too many things at once. Extract functionality which clearly belongs together.

[Documentation](https://detekt.dev/docs/rules/complexity#toomanyfunctions)

* exts/figmagen-kotlin-compose-ext/main/kotlin/com/stuart/figmagen/kotlin/compose/extensions/internal/TreeToKotlinDeclarationExtensions.kt:1:1
```
File '/home/runner/work/figmagen/figmagen/exts/figmagen-kotlin-compose-ext/main/kotlin/com/stuart/figmagen/kotlin/compose/extensions/internal/TreeToKotlinDeclarationExtensions.kt' with '13' functions detected. Defined threshold inside files is set to '11'
```
```kotlin
1 package com.stuart.figmagen.kotlin.compose.extensions.internal
! ^ error
2 
3 import com.stuart.figmagen.models.ColorFile
4 import com.stuart.figmagen.models.parentDirectories

```

* figmagen-core/main/kotlin/com/stuart/figmagen/tree/TreeNode.kt:3:19
```
Class 'TreeNode' with '11' functions detected. Defined threshold inside classes is set to '11'
```
```kotlin
1 package com.stuart.figmagen.tree
2 
3 public data class TreeNode<T>
!                   ^ error
4 private constructor(
5     public val value: T,
6     private val _children: MutableList<TreeNode<T>> = mutableListOf()

```

### naming, ConstructorParameterNaming (1)

Constructor parameter names should follow the naming convention set in the projects configuration.

[Documentation](https://detekt.dev/docs/rules/naming#constructorparameternaming)

* figmagen-core/main/kotlin/com/stuart/figmagen/tree/TreeNode.kt:6:5
```
Constructor private parameter names should match the pattern: [a-z][A-Za-z0-9]*
```
```kotlin
3  public data class TreeNode<T>
4  private constructor(
5      public val value: T,
6      private val _children: MutableList<TreeNode<T>> = mutableListOf()
!      ^ error
7  ) : Iterable<TreeNode<T>> {
8  
9      public constructor(value: T) : this(value, mutableListOf())

```

### potential-bugs, IteratorNotThrowingNoSuchElementException (1)

The `next()` method of an `Iterator` implementation should throw a `NoSuchElementException` when there are no more elements to return.

[Documentation](https://detekt.dev/docs/rules/potential-bugs#iteratornotthrowingnosuchelementexception)

* figmagen-core/main/kotlin/com/stuart/figmagen/tree/PreOrderTreeIterator.kt:5:16
```
This implementation of Iterator does not correctly implement the next() method as it doesn't throw a NoSuchElementException when no elements remain in the Iterator.
```
```kotlin
2 
3 import java.util.Stack
4 
5 internal class PreOrderTreeIterator<T>(root: TreeNode<T>) : Iterator<TreeNode<T>> {
!                ^ error
6 
7     private val stack = Stack<TreeNode<T>>()
8 

```

### style, MagicNumber (3)

Report magic numbers. Magic number is a numeric literal that is not defined as a constant and hence it's unclear what the purpose of this number is. It's better to declare such numbers as constants and give them a proper name. By default, -1, 0, 1, and 2 are not considered to be magic numbers.

[Documentation](https://detekt.dev/docs/rules/style#magicnumber)

* figmagen-core/main/kotlin/com/stuart/figmagen/internal/GetStyles.kt:18:33
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
15             "The request body for getting the Figma styles must not be null, check the file provided"
16         }
17 
18     return if (response.code == 200) {
!!                                 ^ error
19         val figmaStyleWrapper: FigmaApi.StyleWrapper =
20             checkNotNull(styleWrapperJsonAdapter.fromJson(content)) {
21                 "There were an issue parsing the content to `FigmaApi.StyleWrapper`"

```

* figmagen-core/main/kotlin/com/stuart/figmagen/tasks/ColorsTask.kt:83:30
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
80                 "The request body for getting the Figma colors must not be null, check the file provided"
81             }
82 
83         if (response.code == 200) {
!!                              ^ error
84             val data: FigmaApi.File =
85                 checkNotNull(fileJsonAdapter.fromJson(content)) {
86                     "There were an issue parsing the content to `FigmaApi.File`"

```

* figmagen-core/main/kotlin/com/stuart/figmagen/tasks/ColorsTask.kt:95:61
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
92                     val path: String = value.document.name
93                     val pathAsList: List<String> = path.split("/")
94                     val isValidColor: Boolean =
95                         color != null && pathAsList.size >= 3 && pathAsList.isColor
!!                                                             ^ error
96 
97                     if (color != null && isValidColor) {
98                         val rgba: Color.RGBA =

```

### style, NewLineAtEndOfFile (2)

Checks whether files end with a line separator.

[Documentation](https://detekt.dev/docs/rules/style#newlineatendoffile)

* build.gradle.kts:21:2
```
The file /home/runner/work/figmagen/figmagen/build.gradle.kts is not ending with a new line.
```
```kotlin
18         }
19         nexus()
20     }
21 }
!!  ^ error

```

* settings.gradle.kts:20:2
```
The file /home/runner/work/figmagen/figmagen/settings.gradle.kts is not ending with a new line.
```
```kotlin
17 
18 plugins {
19     id("com.javiersc.hubdle.settings")
20 }
!!  ^ error

```

### style, UnnecessaryAbstractClass (1)

An abstract class is unnecessary and can be refactored. An abstract class should have both abstract and concrete properties or functions. An abstract class without a concrete member can be refactored to an interface. An abstract class without an abstract member can be refactored to a concrete class.

[Documentation](https://detekt.dev/docs/rules/style#unnecessaryabstractclass)

* figmagen-core/main/kotlin/com/stuart/figmagen/Figmagen.kt:21:23
```
An abstract class without an abstract member can be refactored to a concrete class.
```
```kotlin
18  *
19  * `Figmagen::generate` function is `suspend`, it is necessary to use Kotlin Coroutines to run it.
20  */
21 public abstract class Figmagen internal constructor(internal val figmaToken: String) {
!!                       ^ error
22 
23     private val tasks: MutableList<Task> = mutableListOf()
24 

```

generated with [detekt version 1.21.0](https://detekt.dev/) on 2022-11-15 14:28:45 UTC
