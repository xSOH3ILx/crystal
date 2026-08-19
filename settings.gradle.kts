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

rootProject.name = "crystal"

// Core modules
include(":app")
include(":core:model")
include(":core:common")
include(":core:database")
include(":core:network")
include(":core:designsystem")

// Feature modules
include(":feature:dashboard")
include(":feature:assets")
include(":feature:transactions")
include(":feature:cashflow")
include(":feature:goals")
include(":feature:liabilities")
include(":feature:cheques")
include(":feature:settings")
