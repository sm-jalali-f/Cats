pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Cats"
include(":app")
include(":datasource:remote:theCatApi")
include(":datasource:local:roomDatabase")
include(":catbreed:domain")
include(":catbreed:presentation")
include(":catbreed:repository")
include(":core:util")
