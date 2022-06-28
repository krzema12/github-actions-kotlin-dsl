package it.krzeminski.githubactions.dsl

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import it.krzeminski.githubactions.dsl.expressions.Contexts
import it.krzeminski.githubactions.dsl.expressions.Env
import it.krzeminski.githubactions.dsl.expressions.expr

@Suppress("VariableNaming")
class ExprUnitTest : FunSpec({
    val dollar = '$'.toString()

    test("Environment variables") {
        assertSoftly {
            val DAY_OF_WEEK by Env.map
            "$DAY_OF_WEEK == 'Monday'" shouldBe "${dollar}DAY_OF_WEEK == 'Monday'"

            "${Env.CI} == true && ${Env.GITHUB_ACTIONS} == true" shouldBe
                "${dollar}CI == true && ${dollar}GITHUB_ACTIONS == true"
        }
    }

    test("Secrets") {
        assertSoftly {
            expr { secrets.GITHUB_TOKEN } shouldBe expr("secrets.GITHUB_TOKEN")

            val NPM_TOKEN by Contexts.secrets.map
            expr { NPM_TOKEN } shouldBe expr("secrets.NPM_TOKEN")
        }
    }

    test("Job context") {
        assertSoftly {
            val expected = "pg_isready -h localhost -p $dollar{{ job.services.postgres.ports[5432] }}"
            val postgres by Contexts.job.services
            val actual = "pg_isready -h localhost -p " + expr { postgres.ports + "[5432]" }
            actual shouldBe expected

            expr { job.container.id } shouldBe expr("job.container.id")
            expr { job.container.network } shouldBe expr("job.container.network")
        }
    }

    test("Runner context") {
        assertSoftly {
            expr { runner.name } shouldBe expr("runner.name")
            expr { runner.os } shouldBe expr("runner.os")
            expr { runner.arch } shouldBe expr("runner.arch")
        }
    }

    test("Strategy context") {
        assertSoftly {
            expr { strategy.`fail-fast` } shouldBe expr("strategy.fail-fast")
            expr { strategy.`max-parallel` } shouldBe expr("strategy.max-parallel")
        }
    }

    test("Matrix context") {
        assertSoftly {
            expr { matrix.os } shouldBe expr("matrix.os")
            val node by Contexts.matrix.map
            expr { node } shouldBe expr("matrix.node")
        }
    }

    test("GitHub context") {
        assertSoftly {
            expr { github.token } shouldBe expr("github.token")
            expr { github.job } shouldBe expr("github.job")
            expr { github.sha } shouldBe expr("github.sha")
            expr { github.repository_owner } shouldBe expr("github.repository_owner")
            expr { github.repositoryUrl } shouldBe expr("github.repositoryUrl")
            expr { github.action } shouldBe expr("github.action")
        }
    }

    test("Functions") {
        assertSoftly {
            expr { always() } shouldBe expr("always()")
            expr { success() } shouldBe expr("success()")
            expr { always() } shouldBe expr("always()")
            expr { failure() } shouldBe expr("failure()")
            expr { cancelled() } shouldBe expr("cancelled()")
            expr { toJSON("job") } shouldBe expr("toJSON(job)")

            expr { contains("he", "hello", quote = true) } shouldBe
                expr("contains('he', 'hello')")

            expr { fromJSON("needs.job1.outputs.matrix") } shouldBe
                expr("fromJSON(needs.job1.outputs.matrix)")

            expr { format("Hello {0} {1} {2}", "Mona", "the", "Octocat", quote = true) } shouldBe
                expr("format('Hello {0} {1} {2}', 'Mona', 'the', 'Octocat')")

            expr { hashFiles("**/package-lock.json", "**/Gemfile.lock", quote = true) } shouldBe
                expr("hashFiles('**/package-lock.json', '**/Gemfile.lock')")

            expr { startsWith("Hello world", "He", quote = true) } shouldBe
                expr("startsWith('Hello world', 'He')")

            expr { endsWith("Hello world", "ld", quote = true) } shouldBe
                expr("endsWith('Hello world', 'ld')")

            expr { join("github.event.issue.labels.*.name", "', '") } shouldBe
                expr("join(github.event.issue.labels.*.name, ', ')")
        }
    }
})
