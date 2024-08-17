// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
	alias(libs.plugins.androidApplication) apply false
	alias(libs.plugins.jetbrainsKotlinAndroid) apply false
	alias(libs.plugins.ksp) apply false
	alias(libs.plugins.hilt.gradle) apply false
	alias(libs.plugins.google.services) apply false
	alias(libs.plugins.androidLibrary) apply false
}

tasks.register("printModulePaths") {
	subprojects {
		if (subprojects.size == 0) {
			println(this.path)
		}
	}
}