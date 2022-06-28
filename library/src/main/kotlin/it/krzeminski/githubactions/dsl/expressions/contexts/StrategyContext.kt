package it.krzeminski.githubactions.dsl.expressions.contexts

import it.krzeminski.githubactions.dsl.expressions.ExpressionContext

/**
 * Strategy context
 *
 * For workflows with a build matrix, the strategy context contains information about the matrix execution strategy
 * for the current job.
 * https://docs.github.com/en/actions/learn-github-actions/contexts#strategy-context
 */
@Suppress("ObjectPropertyNaming", "VariableNaming")
object StrategyContext : ExpressionContext("strategy") {
    val `fail-fast` by map
    val `job-index` by map
    val `job-total` by map
    val `max-parallel` by map
}
