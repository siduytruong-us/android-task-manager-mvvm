plugins {
	alias(libs.plugins.androidLibrary)
	alias(libs.plugins.jetbrainsKotlinAndroid)
	alias(libs.plugins.ksp)
}

android {
	namespace = "com.duyts.core.firebase"
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

	//firebase
	implementation(platform(libs.firebase.bom))
	implementation(libs.firebase.auth.ktx)
	implementation(libs.play.services.auth)

	api(project(":core:common"))
	ksp(libs.hilt.compiler)
}