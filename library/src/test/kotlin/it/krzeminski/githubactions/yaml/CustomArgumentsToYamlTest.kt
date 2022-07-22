package it.krzeminski.githubactions.yaml

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import it.krzeminski.githubactions.dsl.HasCustomArguments

class CustomArgumentsToYamlTest : DescribeSpec({
    describe("customArgumentsToYaml") {
        it("returns an empty string when there are no custom arguments") {
            // given
            val customArguments = object : HasCustomArguments {
                override val _customArguments = emptyMap<String, Any>()
            }

            // when
            val asYaml = customArguments.customArgumentsToYaml()

            asYaml shouldBe ""
        }

        it("works for top-level values") {
            // given
            val customArguments = object : HasCustomArguments {
                override val _customArguments = mapOf(
                    "some-string" to "hello world!",
                    "some-list-of-integers" to listOf(1, 2, 3),
                    "some-list-of-strings" to listOf("foo", "bar", "baz"),
                )
            }

            // when
            val asYaml = customArguments.customArgumentsToYaml()

            asYaml shouldBe """
                 some-string: hello world!
                 some-list-of-integers:
                 - 1
                 - 2
                 - 3
                 some-list-of-strings:
                 - foo
                 - bar
                 - baz
            """.trimIndent()
        }

        it("works for objects with basic values") {
            // given
            val customArguments = object : HasCustomArguments {
                override val _customArguments = mapOf(
                    "some-object" to mapOf("foo" to "1", "bar" to "2"),
                )
            }

            // when
            val asYaml = customArguments.customArgumentsToYaml()

            asYaml shouldBe """
                some-object:
                  foo: '1'
                  bar: '2'
            """.trimIndent()
        }
    }
})
