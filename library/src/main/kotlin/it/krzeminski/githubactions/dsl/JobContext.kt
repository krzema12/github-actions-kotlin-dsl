package it.krzeminski.githubactions.dsl

/**
 * The job context contains information about the currently running job.
 * https://docs.github.com/en/actions/learn-github-actions/contexts#job-context
 */
object JobContext : ContextPath("job") {
    val container = Container
    object Container : ContextPath("job.container") {
        val id by map
        val network by map
    }
    val services = emptyMap<String, JobContextService>()
        .withDefault { key -> JobContextService("$path.services.$key") }
}

class JobContextService(path: String): ContextPath(path) {
    val id by map
    val network by map
    val ports by map
}
