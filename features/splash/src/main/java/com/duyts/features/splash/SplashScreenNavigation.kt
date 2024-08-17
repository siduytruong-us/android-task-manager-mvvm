package com.duyts.features.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val SPLASH_ROUTE = "SPLASH_ROUTE"

fun NavGraphBuilder.splashScreen(onAuthenticate: (Boolean) -> Unit) {
	composable(SPLASH_ROUTE) {
		SplashScreen(onAuthenticate = onAuthenticate)
	}
}