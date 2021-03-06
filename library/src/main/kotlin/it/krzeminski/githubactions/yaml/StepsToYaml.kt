package it.krzeminski.githubactions.yaml

import it.krzeminski.githubactions.actions.fullName
import it.krzeminski.githubactions.domain.CommandStep
import it.krzeminski.githubactions.domain.ExternalActionStep
import it.krzeminski.githubactions.domain.Step

fun List<Step>.stepsToYaml(): String =
    this.joinToString(separator = "\n") {
        it.toYaml()
    }

private fun Step.toYaml() =
    when (this) {
        is ExternalActionStep -> toYaml()
        is CommandStep -> toYaml()
    }

private fun ExternalActionStep.toYaml(): String = buildString {
    appendLine("- id: $id")
    name?.let {
        appendLine("  name: $it")
    }
    continueOnError?.let {
        appendLine("  continue-on-error: $it")
    }
    appendLine("  uses: ${action.fullName}")

    val allArguments = action.toYamlArguments()
    if (allArguments.isNotEmpty()) {
        val arguments = allArguments.toYaml()
        appendLine("  with:")
        appendLine(arguments.prependIndent("    "))
    }
    if (this@toYaml.env.isNotEmpty()) {
        appendLine("  env:")
        appendLine(this@toYaml.env.toYaml().prependIndent("    "))
    }
    customArgumentsToYaml().takeIf { it.isNotBlank() }
        ?.let { freeargs ->
            append(freeargs.replaceIndent("  "))
            appendLine()
        }
    this@toYaml.condition?.let {
        appendLine(it.conditionToYaml())
    }
}.removeSuffix("\n")

private fun CommandStep.toYaml() = buildString {
    appendLine("- id: $id")
    name?.let {
        appendLine("  name: $it")
    }

    if (this@toYaml.env.isNotEmpty()) {
        appendLine("  env:")
        appendLine(this@toYaml.env.toYaml().prependIndent("    "))
    }

    continueOnError?.let {
        appendLine("  continue-on-error: $it")
    }

    customArgumentsToYaml().takeIf { it.isNotBlank() }
        ?.let { freeargs ->
            append(freeargs.replaceIndent("  "))
            appendLine()
        }

    if (command.lines().size == 1) {
        appendLine("  run: $command")
    } else {
        appendLine("  run: |")
        command.lines().forEach {
            appendLine(it.prependIndent("    "))
        }
    }

    this@toYaml.condition?.let {
        appendLine(it.conditionToYaml())
    }
}.removeSuffix("\n")

private fun String.conditionToYaml() =
    "  if: $this"
