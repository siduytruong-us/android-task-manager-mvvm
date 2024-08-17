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

rootProject.name = "Tasks"
include(":app")

include(":core:common")
include(":core:data")
include(":core:network")
include(":core:domain")
include(":core:database")
include(":core:datastore")
include(":core:firebase")
include(":features:splash")
include(":features:home")
include(":core:design")
include(":features:settings")
include(":features:profile")
include(":features:auth")
include(":core:resources")
