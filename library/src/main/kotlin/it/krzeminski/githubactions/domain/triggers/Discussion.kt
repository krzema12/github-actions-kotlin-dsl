package it.krzeminski.githubactions.domain.triggers

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Discussion(
    override val _customArguments: Map<String, @Contextual Any> = mapOf(),
) : Trigger()
