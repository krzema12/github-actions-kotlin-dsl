package it.krzeminski.githubactions.actions.madhead

import it.krzeminski.githubactions.actions.Action

data class CheckGradleVersionV1(
    val gradlew: String? = null,
) : Action(name = "madhead/check-gradle-version@v1") {
    override fun toYamlArguments(): LinkedHashMap<String, String> {
        val result = LinkedHashMap<String, String>()

        gradlew?.let { result["gradlew"] = it }

        return result
    }
}
