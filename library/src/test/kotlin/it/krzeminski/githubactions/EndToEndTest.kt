package it.krzeminski.githubactions

import io.kotest.core.spec.style.FunSpec
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.shouldBe
import it.krzeminski.githubactions.actions.Checkout
import it.krzeminski.githubactions.domain.RunnerType
import it.krzeminski.githubactions.domain.Trigger
import it.krzeminski.githubactions.dsl.workflow
import it.krzeminski.githubactions.yaml.toYaml
import it.krzeminski.githubactions.yaml.writeToFile
import java.nio.file.Paths

class EndToEndTest : FunSpec({
    val sourceFile = tempfile().also {
        it.writeText("Some dummy text that the checksum will be calculated from")
    }
    val workflow = workflow(
        name = "Test workflow",
        on = listOf(Trigger.Push),
        sourceFile = sourceFile.toPath(),
        targetFile = Paths.get(".github/workflows/some_workflow.yaml"),
    ) {
        job(
            name = "test_job",
            runsOn = RunnerType.UbuntuLatest,
        ) {
            uses(
                name = "Check out",
                action = Checkout(),
            )

            run(
                name = "Hello world!",
                command = "echo 'hello!'",
            )
        }
    }

    test("toYaml() - 'hello world' workflow") {
        // when
        val actualYaml = workflow.toYaml()

        // then
        actualYaml shouldBe """
            # This file was generated using Kotlin DSL (${sourceFile.path}).
            # If you want to modify the workflow, please change the Kotlin file and regenerate this YAML file.
            
            name: "Test workflow"
            on:
              push: {}
            jobs:
              "check_yaml_consistency":
                runs-on: "ubuntu-latest"
                steps:
                - 
                  uses: "actions/checkout@v2"
                  with:
                    
                    fetch-depth: 1
                - 
                  name: "Install Kotlin"
                  run: "sudo snap install --classic kotlin"
                - 
                  name: "Consistency check"
                  run: "diff -u '.github/workflows/some_workflow.yaml' <('$sourceFile')"
                needs: []
                strategy: {}
              "test_job":
                runs-on: "ubuntu-latest"
                steps:
                - 
                  uses: "actions/checkout@v2"
                  with:
                    
                    fetch-depth: 1
                - 
                  name: "Hello world!"
                  run: "echo 'hello!'"
                needs:
                - "check_yaml_consistency"
                strategy: {}
        """.trimIndent()
    }

    test("toYaml() - 'hello world' workflow without consistency check") {
        // when
        val actualYaml = workflow.toYaml(addConsistencyCheck = false)

        // then
        actualYaml shouldBe """
            # This file was generated using Kotlin DSL (${sourceFile.path}).
            # If you want to modify the workflow, please change the Kotlin file and regenerate this YAML file.
            
            name: "Test workflow"
            on:
              push: {}
            jobs:
              "test_job":
                runs-on: "ubuntu-latest"
                steps:
                - 
                  uses: "actions/checkout@v2"
                  with:
                    
                    fetch-depth: 1
                - 
                  name: "Hello world!"
                  run: "echo 'hello!'"
                needs: []
                strategy: {}
        """.trimIndent()
    }

    test("writeToFile() - 'hello world' workflow") {
        // given
        val targetTempFile = tempfile()
        val workflowWithTempTargetFile = workflow(
            name = "Test workflow",
            on = listOf(Trigger.Push),
            sourceFile = sourceFile.toPath(),
            targetFile = targetTempFile.toPath(),
        ) {
            job(
                name = "test_job",
                runsOn = RunnerType.UbuntuLatest,
            ) {
                uses(
                    name = "Check out",
                    action = Checkout(),
                )

                run(
                    name = "Hello world!",
                    command = "echo 'hello!'",
                )
            }
        }

        // when
        workflowWithTempTargetFile.writeToFile()

        // then
        targetTempFile.readText() shouldBe """
            # This file was generated using Kotlin DSL (${sourceFile.path}).
            # If you want to modify the workflow, please change the Kotlin file and regenerate this YAML file.
            
            name: "Test workflow"
            on:
              push: {}
            jobs:
              "test_job":
                runs-on: "ubuntu-latest"
                steps:
                - 
                  uses: "actions/checkout@v2"
                  with:
                    
                    fetch-depth: 1
                - 
                  name: "Hello world!"
                  run: "echo 'hello!'"
                needs: []
                strategy: {}
        """.trimIndent()
    }

    test("Using a windows-2022 runner") {
        // given
        val targetTempFile = tempfile()
        val workflowWithTempTargetFile = workflow(
            name = "Test workflow",
            on = listOf(Trigger.Push),
            sourceFile = sourceFile.toPath(),
            targetFile = targetTempFile.toPath(),
        ) {
            job(
                name = "test_job",
                runsOn = RunnerType.Windows2022,
            ) {
                uses(
                    name = "Check out",
                    action = Checkout(),
                )

                run(
                    name = "Hello world!",
                    command = "echo 'hello!'",
                )
            }
        }

        // when
        workflowWithTempTargetFile.writeToFile()

        // then
        targetTempFile.readText() shouldBe """
            # This file was generated using Kotlin DSL (${sourceFile.path}).
            # If you want to modify the workflow, please change the Kotlin file and regenerate this YAML file.
            
            name: "Test workflow"
            on:
              push: {}
            jobs:
              "test_job":
                runs-on: "windows-2022"
                steps:
                - 
                  uses: "actions/checkout@v2"
                  with:
                    
                    fetch-depth: 1
                - 
                  name: "Hello world!"
                  run: "echo 'hello!'"
                needs: []
                strategy: {}
        """.trimIndent()
    }
})
