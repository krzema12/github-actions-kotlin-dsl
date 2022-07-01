package it.krzeminski.githubactions.wrappergenerator.lockfile

import it.krzeminski.githubactions.wrappergenerator.domain.ActionCoords
import it.krzeminski.githubactions.wrappergenerator.versions.Version
import it.krzeminski.githubactions.wrappergenerator.versions.fetchAvailableVersions
import it.krzeminski.githubactions.wrappergenerator.wrappersToGenerate

fun main() {
    val githubToken = System.getenv("GITHUB_TOKEN")
        ?: error(
            """
            |Missing environment variable export GITHUB_TOKEN=token
            |Create a personal token at https://github.com/settings/tokens
            |The token needs to have public_repo scope.
            """.trimMargin()
        )

    wrappersToGenerate
        .associate { request -> request.actionCoords to request.actionCoords.fetchCommitHash(githubToken) }
        .storeInLockfile()
}

private fun ActionCoords.fetchCommitHash(githubToken: String): CommitHash {
    val available = this.fetchAvailableVersions(githubToken)
    val desired: List<Version> = available.filter { it.version == this.version }
    desired
}
