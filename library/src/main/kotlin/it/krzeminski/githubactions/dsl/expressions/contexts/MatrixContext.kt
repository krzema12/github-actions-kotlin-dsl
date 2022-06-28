package it.krzeminski.githubactions.dsl.expressions.contexts

import it.krzeminski.githubactions.dsl.expressions.ExpressionContext

/**
 * matrix context
 * For workflows with a build matrix, the matrix context contains the matrix properties
 * defined in the workflow file that apply to the current job.
 * https://docs.github.com/en/actions/using-jobs/using-a-build-matrix-for-your-jobs
 * https://docs.github.com/en/actions/learn-github-actions/contexts#matrix-context
 */
object MatrixContext : ExpressionContext("matrix") {
    val os by map
}
