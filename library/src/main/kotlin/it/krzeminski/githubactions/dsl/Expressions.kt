package it.krzeminski.githubactions.dsl

/**
 * Creates an expression, i.e. something evaluated by GitHub.
 *
 * @see https://docs.github.com/en/actions/learn-github-actions/expressions#about-expressions
 */
fun expr(value: String) = "\${{ $value }}"


data class EnvironmentVariable(val name: String) {
    override fun toString() = name
}

open class ContextPath(val path: String) {
    val map = emptyMap<String, String>()
        .withDefault { key -> "$path.$key" }
}

