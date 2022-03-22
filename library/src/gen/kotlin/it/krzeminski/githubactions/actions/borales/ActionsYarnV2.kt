// This file was generated using 'wrapper-generator' module. Don't change it by hand, your changes will
// be overwritten with the next wrapper code regeneration. Instead, consider introducing changes to the
// generator itself.
package it.krzeminski.githubactions.actions.borales

import it.krzeminski.githubactions.actions.Action
import kotlin.String
import kotlin.Suppress
import kotlin.collections.Map
import kotlin.collections.toList
import kotlin.collections.toTypedArray

/**
 * Action: GitHub Action for Yarn
 *
 * Wraps the yarn CLI to enable common yarn commands
 *
 * [Action on GitHub](https://github.com/Borales/actions-yarn)
 */
public class ActionsYarnV2(
    /**
     * Yarn command
     */
    public val cmd: String,
    /**
     * NPM_AUTH_TOKEN
     */
    public val authToken: String? = null,
    /**
     * NPM_REGISTRY_URL
     */
    public val registryUrl: String? = null,
    /**
     * Type-unsafe map where you can put any inputs that are not yet supported by the wrapper
     */
    public val _customArguments: Map<String, String> = mapOf()
) : Action("Borales", "actions-yarn", "v2.3.0") {
    @Suppress("SpreadOperator")
    public override fun toYamlArguments() = linkedMapOf(
        *listOfNotNull(
            "cmd" to cmd,
            authToken?.let { "auth-token" to it },
            registryUrl?.let { "registry-url" to it },
            *_customArguments.toList().toTypedArray(),
        ).toTypedArray()
    )
}
