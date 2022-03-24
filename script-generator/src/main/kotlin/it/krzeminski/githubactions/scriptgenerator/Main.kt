package it.krzeminski.githubactions.scriptgenerator

import it.krzeminski.githubactions.scriptmodel.YamlWorkflow
import it.krzeminski.githubactions.scriptmodel.myYaml
import it.krzeminski.githubactions.scriptmodel.normalizeYaml
import kotlinx.serialization.decodeFromString
import java.net.URL

fun main(args: Array<String>) {
    if (args.isEmpty() || args.first().startsWith("http").not()) {
        error(
            """
            Usage:
              ./gradlew :script-generator:run --args https://raw.githubusercontent.com/jmfayard/refreshVersions/main/.github/workflows/publish-mkdocs-website.yml
            """.trimIndent()
        )
    }
    val url = URL(args.first())
    val urlContent = url.readText()
    val workflow: YamlWorkflow = decodeYamlWorkflow(urlContent)
    println(workflow.toKotlin(url.filename()))
}

fun decodeYamlWorkflow(text: String): YamlWorkflow {
    return myYaml.decodeFromString(text.normalizeYaml())
}

fun URL.filename(): String =
    path.substringAfterLast("/").removeSuffix(".yml")