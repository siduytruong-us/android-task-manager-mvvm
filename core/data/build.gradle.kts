plugins {
	alias(libs.plugins.androidLibrary)
	alias(libs.plugins.jetbrainsKotlinAndroid)
	alias(libs.plugins.ksp)
}

android {
	namespace = "com.duyts.core.data"
	compileSdk = 34

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
}

dependencies {
	api(project(":core:common"))
	api(project(":core:database"))
	api(project(":core:datastore"))
	api(project(":core:firebase"))
	ksp(libs.hilt.compiler)


}