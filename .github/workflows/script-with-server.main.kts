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
import it.krzeminski.githubactions.dsl.JobBuilder
import it.krzeminski.githubactions.dsl.workflow
import it.krzeminski.githubactions.yaml.writeToFile
import java.io.File
import java.nio.file.Paths

val logicHolders: MutableList<() -> Unit> = mutableListOf()

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
        startServer() // Probably will be hidden somewhere.
        runKotlin {
            println("Hello from action logic! (doesn't work yet)")
            File("output-first.txt").writeText("Written from first Kotlin logic")
        }
        run(
            name = "Read written file 1",
            command = "cat output-first.txt",
        )
        runKotlin {
            File("output-second.txt").writeText((1..100).joinToString(","))
        }
        run(
            name = "Read written file 2",
            command = "cat output-second.txt",
        )
    }
}

fun JobBuilder.runKotlin(logic: () -> Unit) {
    run(
        name = "Call the server",
        command = "curl http://localhost:8123/action-logic/${logicHolders.size}",
    )
    logicHolders += logic
}

scriptWithServerWorkflow.writeToFile(addConsistencyCheck = false)
startServerIfRunningOnGitHub()

fun startServerIfRunningOnGitHub() {
    if (System.getenv("GITHUB_ACTIONS") != "true") {
        println("Not running on GitHub - not running the server")
        return
    }

    println("Starting server")

    embeddedServer(Netty, port = 8123) {
        routing {
            route("/action-logic") {
                logicHolders.indices.forEach { logicIndex ->
                    get("/$logicIndex") {
                        logicHolders[logicIndex]()
                        call.respondText("Response from API")
                    }
                }
            }
        }
    }.start(wait = true)
}

fun JobBuilder.startServer() {
    run(
        name = "Start server in background",
        command = ".github/workflows/script-with-server.main.kts &",
    )
    run(
        name = "Wait for the server to start",
        command = "while netstat -lnt | awk '\$4 ~ /:8123\$/ {exit 1}'; do sleep 1; done",
    )
}
