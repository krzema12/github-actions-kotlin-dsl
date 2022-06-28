package it.krzeminski.githubactions.dsl.expressions.contexts

import it.krzeminski.githubactions.dsl.expressions.ExpressionContext
import it.krzeminski.githubactions.dsl.expressions.MapFromLambda

/**
 * The job context contains information about the currently running job.
 * https://docs.github.com/en/actions/learn-github-actions/contexts#job-context
 */
object JobContext : ExpressionContext("job") {
    val status by map
    val container = Container
    object Container : ExpressionContext("job.container") {
        val id by map
        val network by map
    }
    val services: Map<String, JobContextService> =
        MapFromLambda { key -> JobContextService("$_path.services.$key") }
}

class JobContextService(path: String) : ExpressionContext(path) {
    val id by map
    val network by map
    val ports by map
}
