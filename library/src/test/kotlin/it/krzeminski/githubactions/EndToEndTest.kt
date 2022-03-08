@file:Suppress("DEPRECATION")

package it.krzeminski.githubactions

import io.kotest.core.spec.style.FunSpec
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.shouldBe
import it.krzeminski.githubactions.actions.actions.CheckoutV2
import it.krzeminski.githubactions.actions.endbug.AddAndCommitV8
import it.krzeminski.githubactions.domain.RunnerType
import it.krzeminski.githubactions.domain.triggers.Push
import it.krzeminski.githubactions.dsl.expr
import it.krzeminski.githubactions.dsl.workflow
import it.krzeminski.githubactions.yaml.toYaml
import it.krzeminski.githubactions.yaml.writeToFile
import java.nio.file.Paths

class EndToEndTest : FunSpec({
    val workflow = workflow(
        name = "Test workflow",
        on = listOf(Push()),
        sourceFile = Paths.get(".github/workflows/some_workflow.main.kts"),
        targetFile = Paths.get(".github/workflows/some_workflow.yaml"),
    ) {
        job(
            name = "test_job",
            runsOn = RunnerType.UbuntuLatest,
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

    test("toYaml() - 'hello world' workflow") {
        // when
        val actualYaml = workflow.toYaml()

        // then
        actualYaml shouldBe """
            # This file was generated using Kotlin DSL (.github/workflows/some_workflow.main.kts).
            # If you want to modify the workflow, please change the Kotlin file and regenerate this YAML file.
            # Generated with https://github.com/krzema12/github-actions-kotlin-dsl
            
            name: Test workflow

            on:
              push:

            jobs:
              "check_yaml_consistency":
                runs-on: "ubuntu-latest"
                steps:
                  - id: step-0
                    name: Check out
                    uses: actions/checkout@v2
                  - id: step-1
                    name: Install Kotlin
                    run: sudo snap install --classic kotlin
                  - id: step-2
                    name: Consistency check
                    run: diff -u '.github/workflows/some_workflow.yaml' <('.github/workflows/some_workflow.main.kts')
              "test_job":
                runs-on: "ubuntu-latest"
                needs:
                  - "check_yaml_consistency"
                steps:
                  - id: step-0
                    name: Check out
                    uses: actions/checkout@v2
                  - id: step-1
                    name: Hello world!
                    run: echo 'hello!'
        """.trimIndent()
    }

    test("toYaml() - workflow with one job depending on another") {
        // given
        val workflowWithDependency = workflow(
            name = "Test workflow",
            on = listOf(Push()),
            sourceFile = Paths.get(".github/workflows/some_workflow.main.kts"),
            targetFile = Paths.get(".github/workflows/some_workflow.yaml"),
        ) {
            val testJob1 = job(
                name = "test_job_1",
                runsOn = RunnerType.UbuntuLatest,
            ) {
                run(
                    name = "Hello world!",
                    command = "echo 'hello!'",
                )
            }

            job(
                name = "test_job_2",
                runsOn = RunnerType.UbuntuLatest,
                needs = listOf(testJob1),
            ) {
                run(
                    name = "Hello world, again!",
                    command = "echo 'hello again!'",
                )
            }
        }

        // when
        val actualYaml = workflowWithDependency.toYaml()

        // then
        actualYaml shouldBe """
            # This file was generated using Kotlin DSL (.github/workflows/some_workflow.main.kts).
            # If you want to modify the workflow, please change the Kotlin file and regenerate this YAML file.
            # Generated with https://github.com/krzema12/github-actions-kotlin-dsl
            
            name: Test workflow

            on:
              push:

            jobs:
              "check_yaml_consistency":
                runs-on: "ubuntu-latest"
                steps:
                  - id: step-0
                    name: Check out
                    uses: actions/checkout@v2
                  - id: step-1
                    name: Install Kotlin
                    run: sudo snap install --classic kotlin
                  - id: step-2
                    name: Consistency check
                    run: diff -u '.github/workflows/some_workflow.yaml' <('.github/workflows/some_workflow.main.kts')
              "test_job_1":
                runs-on: "ubuntu-latest"
                needs:
                  - "check_yaml_consistency"
                steps:
                  - id: step-0
                    name: Hello world!
                    run: echo 'hello!'
              "test_job_2":
                runs-on: "ubuntu-latest"
                needs:
                  - "test_job_1"
                  - "check_yaml_consistency"
                steps:
                  - id: step-0
                    name: Hello world, again!
                    run: echo 'hello again!'
        """.trimIndent()
    }

    test("toYaml() - 'hello world' workflow without consistency check") {
        // when
        val actualYaml = workflow.toYaml(addConsistencyCheck = false)

        // then
        actualYaml shouldBe """
            # This file was generated using Kotlin DSL (.github/workflows/some_workflow.main.kts).
            # If you want to modify the workflow, please change the Kotlin file and regenerate this YAML file.
            # Generated with https://github.com/krzema12/github-actions-kotlin-dsl
            
            name: Test workflow

            on:
              push:

            jobs:
              "test_job":
                runs-on: "ubuntu-latest"
                steps:
                  - id: step-0
                    name: Check out
                    uses: actions/checkout@v2
                  - id: step-1
                    name: Hello world!
                    run: echo 'hello!'
        """.trimIndent()
    }

    test("toYaml() - multiline command with pipes") {
        // given
        val workflowWithMultilineCommand = workflow(
            name = "Test workflow",
            on = listOf(Push()),
            sourceFile = Paths.get(".github/workflows/some_workflow.main.kts"),
            targetFile = Paths.get(".github/workflows/some_workflow.yaml"),
        ) {
            job(
                name = "test_job",
                runsOn = RunnerType.UbuntuLatest,
            ) {
                run(
                    name = "Hello world!",
                    command = """
                        less test.txt \
                        | grep -P "foobar" \
                        | sort \
                        > result.txt
                    """.trimIndent(),
                )
            }
        }

        // when
        val actualYaml = workflowWithMultilineCommand.toYaml(addConsistencyCheck = false)

        // then
        actualYaml shouldBe """
            # This file was generated using Kotlin DSL (.github/workflows/some_workflow.main.kts).
            # If you want to modify the workflow, please change the Kotlin file and regenerate this YAML file.
            # Generated with https://github.com/krzema12/github-actions-kotlin-dsl

            name: Test workflow

            on:
              push:

            jobs:
              "test_job":
                runs-on: "ubuntu-latest"
                steps:
                  - id: step-0
                    name: Hello world!
                    run: |
                      less test.txt \
                      | grep -P "foobar" \
                      | sort \
                      > result.txt
        """.trimIndent()
    }

    test("writeToFile() - 'hello world' workflow") {
        // given
        val targetTempFile = tempfile()
        val workflowWithTempTargetFile = workflow(
            name = "Test workflow",
            on = listOf(Push()),
            sourceFile = Paths.get(".github/workflows/some_workflow.main.kts"),
            targetFile = targetTempFile.toPath(),
        ) {
            job(
                name = "test_job",
                runsOn = RunnerType.UbuntuLatest,
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

        // when
        workflowWithTempTargetFile.writeToFile()

        // then
        targetTempFile.readText() shouldBe """
            # This file was generated using Kotlin DSL (.github/workflows/some_workflow.main.kts).
            # If you want to modify the workflow, please change the Kotlin file and regenerate this YAML file.
            # Generated with https://github.com/krzema12/github-actions-kotlin-dsl
            
            name: Test workflow

            on:
              push:

            jobs:
              "test_job":
                runs-on: "ubuntu-latest"
                steps:
                  - id: step-0
                    name: Check out
                    uses: actions/checkout@v2
                  - id: step-1
                    name: Hello world!
                    run: echo 'hello!'
        """.trimIndent()
    }

    test("toYaml() - job condition") {
        // when
        val actualYaml = workflow(
            name = "Test workflow",
            on = listOf(Push()),
            sourceFile = Paths.get(".github/workflows/some_workflow.main.kts"),
            targetFile = Paths.get(".github/workflows/some_workflow.yaml"),
        ) {
            job(
                name = "test_job",
                runsOn = RunnerType.UbuntuLatest,
                condition = "\${{ always() }}"
            ) {
                uses(
                    name = "Check out",
                    action = CheckoutV2(),
                )
            }
        }.toYaml(addConsistencyCheck = false)

        // then
        actualYaml shouldBe """
            # This file was generated using Kotlin DSL (.github/workflows/some_workflow.main.kts).
            # If you want to modify the workflow, please change the Kotlin file and regenerate this YAML file.
            # Generated with https://github.com/krzema12/github-actions-kotlin-dsl
            
            name: Test workflow

            on:
              push:

            jobs:
              "test_job":
                runs-on: "ubuntu-latest"
                if: ${'$'}{{ always() }}
                steps:
                  - id: step-0
                    name: Check out
                    uses: actions/checkout@v2
        """.trimIndent()
    }

    test("toYaml() - environment variables") {
        // when
        val actualYaml = workflow(
            name = "Test workflow",
            on = listOf(Push()),
            env = linkedMapOf(
                "SIMPLE_VAR" to "simple-value-workflow",
                "MULTILINE_VAR" to """
                    hey,
                    hi,
                    hello! workflow
                """.trimIndent()
            ),
            sourceFile = Paths.get(".github/workflows/some_workflow.main.kts"),
            targetFile = Paths.get(".github/workflows/some_workflow.yaml"),
        ) {
            job(
                name = "test_job",
                runsOn = RunnerType.UbuntuLatest,
                condition = "\${{ always() }}",
                env = linkedMapOf(
                    "SIMPLE_VAR" to "simple-value-job",
                    "MULTILINE_VAR" to """
                        hey,
                        hi,
                        hello! job
                    """.trimIndent()
                ),
            ) {
                uses(
                    name = "Check out",
                    action = CheckoutV2(),
                    env = linkedMapOf(
                        "SIMPLE_VAR" to "simple-value-uses",
                        "MULTILINE_VAR" to """
                            hey,
                            hi,
                            hello! uses
                        """.trimIndent()
                    ),
                )

                run(
                    name = "Hello world!",
                    command = "echo 'hello!'",
                    env = linkedMapOf(
                        "SIMPLE_VAR" to "simple-value-run",
                        "MULTILINE_VAR" to """
                            hey,
                            hi,
                            hello! run
                        """.trimIndent()
                    ),
                )
            }
        }.toYaml(addConsistencyCheck = false)

        // then
        actualYaml shouldBe """
            # This file was generated using Kotlin DSL (.github/workflows/some_workflow.main.kts).
            # If you want to modify the workflow, please change the Kotlin file and regenerate this YAML file.
            # Generated with https://github.com/krzema12/github-actions-kotlin-dsl
            
            name: Test workflow

            on:
              push:

            env:
              SIMPLE_VAR: simple-value-workflow
              MULTILINE_VAR: |
                hey,
                hi,
                hello! workflow

            jobs:
              "test_job":
                runs-on: "ubuntu-latest"
                env:
                  SIMPLE_VAR: simple-value-job
                  MULTILINE_VAR: |
                    hey,
                    hi,
                    hello! job
                if: ${'$'}{{ always() }}
                steps:
                  - id: step-0
                    name: Check out
                    uses: actions/checkout@v2
                    env:
                      SIMPLE_VAR: simple-value-uses
                      MULTILINE_VAR: |
                        hey,
                        hi,
                        hello! uses
                  - id: step-1
                    name: Hello world!
                    env:
                      SIMPLE_VAR: simple-value-run
                      MULTILINE_VAR: |
                        hey,
                        hi,
                        hello! run
                    run: echo 'hello!'
        """.trimIndent()
    }

    test("toYaml() - step with outputs") {
        // when
        val actualYaml = workflow(
            name = "Test workflow",
            on = listOf(Push()),
            sourceFile = Paths.get(".github/workflows/some_workflow.main.kts"),
            targetFile = Paths.get(".github/workflows/some_workflow.yaml"),
        ) {
            job(
                name = "test_job",
                runsOn = RunnerType.UbuntuLatest,
            ) {
                val addAndCommit = uses(
                    name = "Some step with output",
                    action = AddAndCommitV8(),
                )

                uses(
                    name = "Some step consuming other step's output",
                    action = CheckoutV2(
                        repository = expr(addAndCommit.id),
                        ref = expr(addAndCommit.outputs.commitSha),
                        token = expr(addAndCommit.outputs["my-unsafe-output"]),
                    )
                )
            }
        }.toYaml(addConsistencyCheck = false)

        // then
        actualYaml shouldBe """
            # This file was generated using Kotlin DSL (.github/workflows/some_workflow.main.kts).
            # If you want to modify the workflow, please change the Kotlin file and regenerate this YAML file.
            # Generated with https://github.com/krzema12/github-actions-kotlin-dsl

            name: Test workflow

            on:
              push:

            jobs:
              "test_job":
                runs-on: "ubuntu-latest"
                steps:
                  - id: step-0
                    name: Some step with output
                    uses: EndBug/add-and-commit@v8
                  - id: step-1
                    name: Some step consuming other step's output
                    uses: actions/checkout@v2
                    with:
                      repository: ${'$'}{{ step-0 }}
                      ref: ${'$'}{{ steps.step-0.outputs.commit_sha }}
                      token: ${'$'}{{ steps.step-0.outputs.my-unsafe-output }}
        """.trimIndent()
    }
})
