rootProject.name = "github-actions-kotlin-dsl"

include(
    "library",
    "wrapper-generator",
    "script-generator"
)

plugins {
    id("com.gradle.enterprise").version("3.8.1")
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlwaysIf(System.getenv("GITHUB_ACTIONS") == "true")
        publishOnFailure()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
