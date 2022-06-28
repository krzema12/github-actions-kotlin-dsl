package it.krzeminski.githubactions.dsl.expressions

import it.krzeminski.githubactions.dsl.expressions.contexts.FunctionsContext
import it.krzeminski.githubactions.dsl.expressions.contexts.GitHubContext
import it.krzeminski.githubactions.dsl.expressions.contexts.JobContext
import it.krzeminski.githubactions.dsl.expressions.contexts.MatrixContext
import it.krzeminski.githubactions.dsl.expressions.contexts.RunnerContext
import it.krzeminski.githubactions.dsl.expressions.contexts.StrategyContext

/**
 * Root elements of GitHub expressions.
 *
 * https://docs.github.com/en/actions/learn-github-actions/expressions#about-expressions
 * https://docs.github.com/en/actions/learn-github-actions/contexts
 */
object Contexts: FunctionsContext() {
    val job: JobContext = JobContext

    val runner: RunnerContext = RunnerContext

    val github: GitHubContext = GitHubContext

    val strategy: StrategyContext = StrategyContext

    val matrix: MatrixContext = MatrixContext

    val env: Env = Env

    val secrets: Secrets = Secrets
}
