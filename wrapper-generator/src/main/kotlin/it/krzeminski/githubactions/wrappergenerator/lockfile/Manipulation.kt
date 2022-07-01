package it.krzeminski.githubactions.wrappergenerator.lockfile

import com.charleskorn.kaml.Yaml
import it.krzeminski.githubactions.wrappergenerator.domain.ActionCoords
import it.krzeminski.githubactions.wrappergenerator.metadata.yamlName
import kotlinx.serialization.encodeToString
import java.nio.file.Path

private val lockfilePath = Path.of("actions.lock")

fun Map<ActionCoords, CommitHash>.storeInLockfile() {
    val lockfile = Lockfile(
        actions = this
            .map { (key, value) -> key.yamlName to ActionLockfileInfo(commitHash = value) }
            .toMap()
    )
    val lockfileYaml = Yaml.default.encodeToString(lockfile)
    lockfilePath.toFile().writeText(lockfileYaml)
}
