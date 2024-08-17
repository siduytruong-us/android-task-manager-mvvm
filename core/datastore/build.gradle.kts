plugins {
	alias(libs.plugins.androidLibrary)
	alias(libs.plugins.jetbrainsKotlinAndroid)
	alias(libs.plugins.protobuf)
	alias(libs.plugins.ksp)
}

android {
	namespace = "com.duyts.core.datastore"
	compileSdk = 34

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}

	kotlinOptions {
		jvmTarget = "1.8"
	}
}

protobuf {
	protoc {
		artifact = libs.protobuf.protoc.get().toString()
	}
	generateProtoTasks {
		all().forEach { task ->
			task.builtins {
				register("java") {
					option("lite")
				}
				register("kotlin") {
					option("lite")
				}
			}
		}
	}
}


dependencies {
	api(project(":core:common"))
	api(project(":core:database"))

	//DataStore - Proto
	implementation  (libs.androidx.datastore)
	implementation  (libs.protobuf.kotlin.lite)
	ksp(libs.hilt.compiler)
}