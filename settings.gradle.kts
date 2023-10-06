rootProject.name = "hr-app"

pluginManagement {
    repositories.gradlePluginPortal()
    includeBuild("gradle/plugins")
}

dependencyResolutionManagement {
    repositories.mavenCentral()
}

include("job-service")
include("candidate-service")

