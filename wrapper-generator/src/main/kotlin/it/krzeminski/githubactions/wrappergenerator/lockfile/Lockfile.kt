package it.krzeminski.githubactions.wrappergenerator.lockfile

import kotlinx.serialization.Serializable

typealias CommitHash = String
typealias ActionCoordsString = String

@Serializable
data class Lockfile(
    val actions: Map<ActionCoordsString, ActionLockfileInfo>,
)

@Serializable
data class ActionLockfileInfo(
    val commitHash: CommitHash,
)
