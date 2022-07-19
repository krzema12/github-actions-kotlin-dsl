@file:Suppress("PropertyName", "VariableNaming", "FunctionNaming", "FunctionName")

package it.krzeminski.githubactions.dsl

import kotlinx.serialization.Serializable

interface HasCustomArguments {
    val _customArguments: Map<String, CustomValue>
}

@Serializable
sealed class CustomValue

@Serializable
data class StringCustomValue(val value: String) : CustomValue()

@Serializable
data class ListCustomValue(val value: List<CustomValue>) : CustomValue()

@Serializable
data class ObjectCustomValue(val value: Map<String, CustomValue>) : CustomValue()

fun IntCustomValue(value: Int): StringCustomValue =
    StringCustomValue("$value")

fun BooleanCustomValue(value: Boolean): StringCustomValue =
    StringCustomValue("$value")

fun ListCustomValue(vararg params: CustomValue): ListCustomValue =
    ListCustomValue(params.toList())
