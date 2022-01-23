package it.krzeminski.githubactions.yaml

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import it.krzeminski.githubactions.actions.Checkout
import it.krzeminski.githubactions.actions.FetchDepth
import it.krzeminski.githubactions.domain.CommandStep
import it.krzeminski.githubactions.domain.ExternalActionStep

class StepsToYamlTest : DescribeSpec({
    it("renders multiple steps") {
        // given
        val steps = listOf(
            CommandStep(
                name = "Some command",
                command = "echo 'test!'",
            ),
            ExternalActionStep(
                name = "Some external action",
                action = Checkout(),
            ),
        )

        // when
        val yaml = steps.stepsToYaml()

        // then
        yaml shouldBe """|- name: Some command
                         |  run: echo 'test!'
                         |- name: Some external action
                         |  uses: actions/checkout@v2""".trimMargin()
    }

    describe("command step") {
        it("renders with required parameters") {
            // given
            val steps = listOf(
                CommandStep(
                    name = "Some command",
                    command = "echo 'test!'",
                ),
            )

            // when
            val yaml = steps.stepsToYaml()

            // then
            yaml shouldBe """|- name: Some command
                             |  run: echo 'test!'""".trimMargin()
        }

        it("renders with condition") {
            // given
            val steps = listOf(
                CommandStep(
                    name = "Some command",
                    command = "echo 'test!'",
                    condition = "\${{ matrix.foo == 'bar' }}"
                ),
            )

            // when
            val yaml = steps.stepsToYaml()

            // then
            yaml shouldBe """|- name: Some command
                             |  if: ${'$'}{{ matrix.foo == 'bar' }}
                             |  run: echo 'test!'""".trimMargin()
        }
    }

    describe("external action step") {
        it("renders with no parameters") {
            // given
            val steps = listOf(
                ExternalActionStep(
                    name = "Some external action",
                    action = Checkout(),
                ),
            )

            // when
            val yaml = steps.stepsToYaml()

            // then
            yaml shouldBe """|- name: Some external action
                             |  uses: actions/checkout@v2""".trimMargin()
        }

        it("renders with some parameters") {
            // given
            val steps = listOf(
                ExternalActionStep(
                    name = "Some external action",
                    action = Checkout(fetchDepth = FetchDepth.Infinite),
                ),
            )

            // when
            val yaml = steps.stepsToYaml()

            // then
            yaml shouldBe """|- name: Some external action
                             |  uses: actions/checkout@v2
                             |  with:
                             |    fetch-depth: 0""".trimMargin()
        }

        it("renders with condition") {
            // given
            val steps = listOf(
                ExternalActionStep(
                    name = "Some external action",
                    action = Checkout(),
                    condition = "\${{ matrix.foo == 'bar' }}"
                ),
            )

            // when
            val yaml = steps.stepsToYaml()

            // then
            yaml shouldBe """|- name: Some external action
                             |  uses: actions/checkout@v2
                             |  if: ${'$'}{{ matrix.foo == 'bar' }}""".trimMargin()
        }
    }
})
