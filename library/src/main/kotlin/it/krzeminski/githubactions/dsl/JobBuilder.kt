package it.krzeminski.githubactions.dsl

import it.krzeminski.githubactions.actions.Action
import it.krzeminski.githubactions.actions.ActionWithOutputs
import it.krzeminski.githubactions.domain.CommandStep
import it.krzeminski.githubactions.domain.ExternalActionStep
import it.krzeminski.githubactions.domain.ExternalActionStepWithOutputs
import it.krzeminski.githubactions.domain.Job
import it.krzeminski.githubactions.domain.RunnerType
import java.nio.file.Path

@Suppress("LongParameterList")
@GithubActionsDsl
class JobBuilder(
    val name: String,
    val runsOn: RunnerType,
    val needs: List<Job>,
    val env: LinkedHashMap<String, String>,
    val condition: String?,
    val strategyMatrix: Map<String, List<String>>?,
    override val _customArguments: Map<String, CustomValue>,
) : HasCustomArguments {
    private var job = Job(
        name = name,
        runsOn = runsOn,
        needs = needs,
        condition = condition,
        env = env,
        steps = emptyList(),
        strategyMatrix = strategyMatrix,
        _customArguments = _customArguments
    )

    fun run(
        name: String,
        command: String,
        env: LinkedHashMap<String, String> = linkedMapOf(),
        condition: String? = null,
    ): CommandStep {
        val newStep = CommandStep(
            id = "step-${job.steps.size}",
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
            id = "step-${job.steps.size}",
            name = name,
            action = action,
            env = env,
            condition = condition,
        )
        job = job.copy(steps = job.steps + newStep)
        return newStep
    }

    fun <T> uses(
        name: String,
        action: ActionWithOutputs<T>,
        env: LinkedHashMap<String, String> = linkedMapOf(),
        condition: String? = null,
    ): ExternalActionStepWithOutputs<T> {
        val stepId = "step-${job.steps.size}"
        val newStep = ExternalActionStepWithOutputs(
            id = stepId,
            name = name,
            action = action,
            env = env,
            condition = condition,
            outputs = action.buildOutputObject(stepId),
        )
        job = job.copy(steps = job.steps + newStep)
        return newStep
    }

    fun runKotlin(
        name: String,
        sourceFile: Path,
        block: () -> Unit,
    ) {
        println(name)
        println(block)
        val serverStepName = "run-kotlin-rest-server"
        if (job.steps.find { it is CommandStep && it.name == serverStepName } == null) {
            run(
                name = serverStepName,
                command = "$sourceFile &"
            )
        }
        val existingRunKotlinStepsCount = job.steps.count { it is CommandStep && "runKotlin" in it.name }
        val runKotlinStepName = "runKotlin-$existingRunKotlinStepsCount"
        run(
            name = "$name-$runKotlinStepName",
            // TODO call the REST service
            command = """
                test
            """.trimIndent()
        )
    }

    fun build() = job
}
