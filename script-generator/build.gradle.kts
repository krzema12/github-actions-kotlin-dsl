import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.6.10"
    application

    // Code quality.
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":wrapper-generator"))
    implementation(project(":library"))
    implementation("com.charleskorn.kaml:kaml:0.42.0")
    implementation("com.squareup:kotlinpoet:1.10.2")

    testImplementation(project(":library"))
    testImplementation("io.kotest:kotest-assertions-core:5.1.0")
    testImplementation("io.kotest:kotest-runner-junit5:5.1.0")
    testImplementation(project(":library"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClass.set("it.krzeminski.githubactions.scriptgenerator.MainKt")
    tasks.run.get().workingDir = rootProject.projectDir
}

tasks.getByName("run") {
    finalizedBy(":library:ktlintFormat")
}

configure<KtlintExtension> {
    filter {
        exclude("**/wrappersfromunittests/**")
    }
}