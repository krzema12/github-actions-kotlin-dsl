#!/usr/bin/env kotlin
@file:DependsOn("it.krzeminski:github-actions-kotlin-dsl:0.11.0")
@file:DependsOn("io.ktor:ktor-server-core:1.6.8")
@file:DependsOn("io.ktor:ktor-server-netty:1.6.8")

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import it.krzeminski.githubactions.actions.actions.CheckoutV3
import it.krzeminski.githubactions.domain.RunnerType
import it.krzeminski.githubactions.domain.triggers.Push
import it.krzeminski.githubactions.dsl.workflow
import it.krzeminski.githubactions.yaml.writeToFile
import java.nio.file.Paths

val scriptWithServerWorkflow = workflow(
    name = "Script with server",
    on = listOf(
        Push(),
    ),
    sourceFile = Paths.get(".github/workflows/script-with-server.main.kts"),
    targetFile = Paths.get(".github/workflows/script-with-server.yaml"),
) {
    job(
        name = "some-job",
        runsOn = RunnerType.UbuntuLatest,
    ) {
        uses(
            name = "Checkout",
            action = CheckoutV3(),
        )
        run(
            name = "Start server in background",
            command = ".github/workflows/script-with-server.main.kts &",
        )
        run(
            name = "Wait for the server to start",
            command = "while netstat -lnt | awk '\$4 ~ /:8123\$/ {exit 1}'; do sleep 1; done",
        )
        (1..10).forEach {
            run(
                name = "Call the server #$it",
                command = "curl http://localhost:8123/action-logic",
            )
        }
    }
}

if (System.getenv("GITHUB_ACTIONS") != "true") {
    scriptWithServerWorkflow.writeToFile(addConsistencyCheck = false)
}
startServerIfRunningOnGitHub()

fun startServerIfRunningOnGitHub() {
    if (System.getenv("GITHUB_ACTIONS") != "true") {
        println("Not running on GitHub - not running the server")
        return
    }

    println("Starting server")

    embeddedServer(Netty, port = 8123) {
        routing {
            get("/action-logic") {
                println("Hello from action logic!")
                call.respondText("Response from API")
            }
        }
    }.start(wait = true)
}
