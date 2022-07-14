package it.krzeminski.githubactions.dsl

import it.krzeminski.githubactions.domain.*
import it.krzeminski.githubactions.domain.triggers.Trigger
import java.nio.file.Path

@GithubActionsDsl
@Suppress("LongParameterList", "FunctionParameterNaming", "ConstructorParameterNaming")
class WorkflowBuilder(
    name: String,
    on: List<Trigger>,
    env: LinkedHashMap<String, String> = linkedMapOf(),
    sourceFile: Path,
    targetFileName: String,
    defaults: Defaults? = null,
    concurrency: Concurrency? = null,
    jobs: List<Job> = emptyList(),
    _customArguments: Map<String, CustomValue>,
) {
    internal var workflow = Workflow(
        name = name,
        on = on,
        env = env,
        sourceFile = sourceFile,
        targetFileName = targetFileName,
        jobs = jobs,
        defaults = defaults,
        concurrency = concurrency,
        _customArguments = _customArguments,
    )

    @Suppress("LongParameterList")
    fun job(
        id: String,
        name: String? = null,
        runsOn: RunnerType,
        needs: List<Job> = emptyList(),
        condition: String? = null,
        env: LinkedHashMap<String, String> = linkedMapOf(),
        strategyMatrix: Map<String, List<String>>? = null,
        _customArguments: Map<String, CustomValue> = mapOf(),
        timeoutMinutes: Int? = null,
        concurrency: Concurrency? = null,
        block: JobBuilder.() -> Unit,
    ): Job {
        val jobBuilder = JobBuilder(
            id = id,
            name = name,
            runsOn = runsOn,
            needs = needs,
            condition = condition,
            env = env,
            strategyMatrix = strategyMatrix,
            timeoutMinutes = timeoutMinutes,
            concurrency = concurrency,
            _customArguments = _customArguments,
        )
        jobBuilder.block()
        val newJob = jobBuilder.build()

        require(newJob.steps.isNotEmpty()) {
            "There are no steps defined!"
        }

        workflow = workflow.copy(jobs = workflow.jobs + newJob)
        return newJob
    }

    fun build() = workflow
}

fun Workflow.toBuilder() =
    WorkflowBuilder(
        name = name,
        on = on,
        sourceFile = sourceFile,
        targetFileName = targetFileName,
        jobs = jobs,
        _customArguments = _customArguments,
    )

@Suppress("LongParameterList", "FunctionParameterNaming")
fun workflow(
    name: String,
    on: List<Trigger>,
    env: LinkedHashMap<String, String> = linkedMapOf(),
    sourceFile: Path,
    targetFileName: String = sourceFile.fileName.toString().substringBeforeLast(".main.kts") + ".yaml",
    defaults: Defaults? = null,
    concurrency: Concurrency? = null,
    _customArguments: Map<String, CustomValue> = mapOf(),
    block: WorkflowBuilder.() -> Unit,
): Workflow {
    require(on.isNotEmpty()) {
        "There are no triggers defined!"
    }

    defaults?.let {
        defaults.run.requireAtLeastOneInput()
    }

    val workflowBuilder = WorkflowBuilder(
        name = name,
        on = on,
        env = env,
        sourceFile = sourceFile,
        targetFileName = targetFileName,
        defaults = defaults,
        concurrency = concurrency,
        _customArguments = _customArguments,
    )
    workflowBuilder.block()

    require(workflowBuilder.workflow.jobs.isNotEmpty()) {
        "There are no jobs defined!"
    }
    workflowBuilder.workflow.jobs.requireUniqueJobIds()

    return workflowBuilder.build()
}

private fun Run.requireAtLeastOneInput() {
    require(this.shell != null || this.workingDirectory != null) {
        "At least one of shell or working-directory must be defined!"
    }
}

private fun List<Job>.requireUniqueJobIds() {
    val countPerJobName = this
        .map { it.id }
        .groupBy { it }
        .mapValues { it.value.count() }

    require(countPerJobName.none { it.value > 1 }) {
        val duplicatedJobNames = countPerJobName
            .filter { it.value > 1 }
            .map { it.key }
        "Duplicated job names: $duplicatedJobNames"
    }
}
