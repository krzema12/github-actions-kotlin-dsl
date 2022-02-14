package it.krzeminski.githubactions.yaml

import it.krzeminski.githubactions.domain.Job
import it.krzeminski.githubactions.domain.RunnerType
import it.krzeminski.githubactions.domain.RunnerType.MacOS1015
import it.krzeminski.githubactions.domain.RunnerType.MacOS11
import it.krzeminski.githubactions.domain.RunnerType.MacOSLatest
import it.krzeminski.githubactions.domain.RunnerType.Ubuntu1804
import it.krzeminski.githubactions.domain.RunnerType.Ubuntu2004
import it.krzeminski.githubactions.domain.RunnerType.UbuntuLatest
import it.krzeminski.githubactions.domain.RunnerType.Windows2016
import it.krzeminski.githubactions.domain.RunnerType.Windows2019
import it.krzeminski.githubactions.domain.RunnerType.Windows2022
import it.krzeminski.githubactions.domain.RunnerType.WindowsLatest

fun List<Job>.jobsToYaml(): String =
    this.joinToString(separator = "\n") {
        it.toYaml()
    }

private fun Job.toYaml() = buildString {
    appendLine("\"$escapedName\":")
    appendLine("  runs-on: \"${runsOn.toYaml()}\"")

    if (this@toYaml.needs.isNotEmpty()) {
        appendLine("  needs:")
        this@toYaml.needs.forEach {
            appendLine("    - \"${it.name}\"")
        }
    }

    if (this@toYaml.env.isNotEmpty()) {
        appendLine("  env:")
        appendLine(this@toYaml.env.toYaml().prependIndent("    "))
    }

    this@toYaml.condition?.let {
        appendLine("  if: $it")
    }

    this@toYaml.strategyMatrix?.let {
        appendLine("  strategy:")
        appendLine("    matrix:")
        it.forEach { (strategyParam, values) ->
            appendLine("      $strategyParam:")
            values.forEach { value ->
                appendLine("        - $value")
            }
        }
    }

    appendLine("  steps:")
    append(steps.stepsToYaml().prependIndent("    "))
}

fun RunnerType.toYaml() =
    when (this) {
        UbuntuLatest -> "ubuntu-latest"
        WindowsLatest -> "windows-latest"
        MacOSLatest -> "macos-latest"
        Windows2022 -> "windows-2022"
        Windows2019 -> "windows-2019"
        Windows2016 -> "windows-2016"
        Ubuntu2004 -> "ubuntu-20.04"
        Ubuntu1804 -> "ubuntu-18.04"
        MacOS11 -> "macos-11"
        MacOS1015 -> "macos-10.15"
    }
