plugins {
	alias(libs.plugins.androidLibrary)
	alias(libs.plugins.jetbrainsKotlinAndroid)
	alias(libs.plugins.hilt.gradle)
	alias(libs.plugins.ksp)
}

android {
	namespace = "com.google.samples.apps.nowinandroid.core.common"
	compileSdk = 34

	defaultConfig {
		minSdk = 24
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
}

dependencies {
	//Compose
	api(libs.androidx.navigation.compose)
	api (libs.androidx.hilt.navigation.compose)
	api(libs.androidx.ui.tooling.preview)
	api(libs.androidx.lifecycle.runtimeCompose)
	//Room
	implementation(libs.room.runtime)
	implementation(libs.room.ktx)
	ksp(libs.androidx.room.compiler)


//	api ("androidx.compose.material:material-icons-core:1.6.8")
	api(platform(libs.androidx.compose.bom))
	api(libs.androidx.ui)
	api(libs.androidx.material3)
	//Hilt
	api(libs.hilt.android)
	ksp(libs.hilt.compiler)


}