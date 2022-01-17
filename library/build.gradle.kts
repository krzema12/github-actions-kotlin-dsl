import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"

    // Code quality.
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"

    // Publishing.
    `maven-publish`
    signing
}

group = "it.krzeminski"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.charleskorn.kaml:kaml:0.40.0")

    testImplementation("io.kotest:kotest-assertions-core:5.0.3")
    testImplementation("io.kotest:kotest-runner-junit5:5.1.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
        allWarningsAsErrors = true
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

java {
    withJavadocJar()
    withSourcesJar()
}

val githubUser = "krzema12"
val libraryName = "github-actions-kotlin-dsl"

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = libraryName
            from(components["java"])

            pom {
                name.set(libraryName)
                description.set("Authoring GitHub Actions in Kotlin.")
                url.set("https://github.com/$githubUser/$libraryName")

                licenses {
                    license {
                        name.set("Apache License, version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/$githubUser/$libraryName.git/")
                    developerConnection.set("scm:git:ssh://github.com:$githubUser/$libraryName.git")
                    url.set("https://github.com/$githubUser/$libraryName.git")
                }

                developers {
                    developer {
                        id.set("krzema12")
                        name.set("Piotr Krzemiński")
                        email.set("git@krzeminski.it")
                    }
                }
            }
        }
    }

    val ossrhUsername: String? by project
    val ossrhPassword: String? by project

    repositories {
        maven(url = "https://oss.sonatype.org/service/local/staging/deploy/maven2/") {
            credentials {
                username = ossrhUsername
                password = ossrhPassword
            }
        }
    }
}

tasks {
    signing {
        sign(publishing.publications["mavenJava"])
    }
}

val validateVersionInReadme by tasks.creating<Task> {
    doLast {
        require(
            project.rootDir.resolve("README.md").readText().let {
                it.contains("implementation(\"it.krzeminski:github-actions-kotlin-dsl:$version\")") &&
                    it.contains("@file:DependsOn(\"it.krzeminski:github-actions-kotlin-dsl:$version\")")
            }
        ) { "Library versions stated in build.gradle.kts and in README.md should be equal!" }
    }
}

tasks.getByName("check").dependsOn(validateVersionInReadme)
