plugins {
	alias(libs.plugins.androidApplication)
	alias(libs.plugins.jetbrainsKotlinAndroid)
	alias(libs.plugins.ksp)
	alias(libs.plugins.hilt.gradle)
	alias(libs.plugins.google.services)
}

android {
	namespace = "com.duyts.tasks"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.duyts.tasks"
		minSdk = 24
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.1"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

dependencies {
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(libs.androidx.ui.tooling.preview)
	implementation(libs.androidx.material3)
	implementation(libs.androidx.lifecycle.runtimeCompose)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.ui.test.junit4)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)

	//Room
	implementation(libs.room.runtime)
	implementation(libs.room.ktx)
	ksp(libs.androidx.room.compiler)

	//Hilt
	implementation(libs.hilt.android)
	ksp(libs.hilt.compiler)

	//DataStore - Proto
	implementation  (libs.androidx.datastore)
	implementation  (libs.protobuf.kotlin.lite)

	implementation (libs.androidx.core.splashscreen)

	implementation(project(":core:common"))
	implementation(project(":core:database"))
	implementation(project(":core:data"))
	implementation(project(":core:datastore"))

	implementation(project(":features:splash"))
	implementation(project(":features:home"))
	implementation(project(":features:profile"))
	implementation(project(":features:auth"))

}