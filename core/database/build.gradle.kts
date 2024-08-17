plugins {
	alias(libs.plugins.androidLibrary)
	alias(libs.plugins.jetbrainsKotlinAndroid)
	alias(libs.plugins.ksp)
}

android {
	namespace = "com.duyts.core.database"
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
	//Room
	implementation(libs.room.runtime)
	implementation(libs.room.ktx)
	ksp(libs.androidx.room.compiler)
	//Hilt
	ksp(libs.hilt.compiler)
}