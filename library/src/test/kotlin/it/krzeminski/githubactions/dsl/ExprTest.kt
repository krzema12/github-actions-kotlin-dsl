package it.krzeminski.githubactions.dsl

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ExprTest :FunSpec({
    val dollar = '$'.toString()

    test("Environment variables") {
        val DAY_OF_WEEK by Expr.env
        expr { "$DAY_OF_WEEK == 'Monday'" } shouldBe expr("DAY_OF_WEEK == 'Monday'")

        expr { "$CI == true && $GITHUB_ACTIONS == true && $GITHUB_REPOSITORY == octocat/Hello-World" } shouldBe
                expr("CI == true && GITHUB_ACTIONS == true && GITHUB_REPOSITORY == octocat/Hello-World")
    }

    test("Job context") {
        val expected = "pg_isready -h localhost -p $dollar{{ job.services.postgres.ports[5432] }}"
        val postgres by Expr.job.services
        val actual = "pg_isready -h localhost -p " + expr { postgres.ports + "[5432]" }
        actual shouldBe expected
    }
})
