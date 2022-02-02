import it.krzeminski.githubactions.actions.actions.CheckoutV2
import it.krzeminski.githubactions.domain.RunnerType.UbuntuLatest
import it.krzeminski.githubactions.domain.triggers.Push
import it.krzeminski.githubactions.dsl.workflow
import it.krzeminski.githubactions.yaml.toYaml

fun main() {
    val workflow = workflow(
        name = "Test workflow",
        on = listOf(Push()),
        sourceFile = "script.main.kts",
        targetFile = "some_workflow.yaml",
    ) {
        job(
            name = "test_job",
            runsOn = UbuntuLatest,
        ) {
            uses(
                name = "Check out",
                action = CheckoutV2(),
            )

            run(
                name = "Hello world!",
                command = "echo 'hello!'",
            )
        }
    }
    println(workflow.toYaml())
}
