package it.krzeminski.githubactions.dsl

import it.krzeminski.githubactions.actions.Action
import it.krzeminski.githubactions.domain.CommandStep
import it.krzeminski.githubactions.domain.ExternalActionStep
import it.krzeminski.githubactions.domain.Job
import it.krzeminski.githubactions.domain.RunnerType

@GithubActionsDsl
class JobBuilder(
    val name: String,
    val runsOn: RunnerType,
    val needs: List<Job>,
    val env: LinkedHashMap<String, String>,
    val condition: String?,
    val strategyMatrix: Map<String, List<String>>?,
) {
    private var job = Job(
        name = name,
        runsOn = runsOn,
        needs = needs,
        condition = condition,
        env = env,
        steps = emptyList(),
        strategyMatrix = strategyMatrix,
    )

    fun run(
        name: String,
        command: String,
        env: LinkedHashMap<String, String> = linkedMapOf(),
        condition: String? = null,
    ): CommandStep {
        val newStep = CommandStep(
            name = name,
            command = command,
            env = env,
            condition = condition,
        )
        job = job.copy(steps = job.steps + newStep)
        return newStep
    }

    fun uses(
        name: String,
        action: Action,
        env: LinkedHashMap<String, String> = linkedMapOf(),
        condition: String? = null,
    ): ExternalActionStep {
        val newStep = ExternalActionStep(
            name = name,
            action = action,
            env = env,
            condition = condition,
        )
        job = job.copy(steps = job.steps + newStep)
        return newStep
    }

    fun build() = job
}
