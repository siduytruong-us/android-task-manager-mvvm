package com.duyts.features.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val LOGIN_ROUTE = "LOGIN_ROUTE"
fun NavController.navigateToLoginScreen() = navigate(LOGIN_ROUTE) {
	popUpTo(graph.findStartDestination().id) {
		inclusive = true
	}
}

fun NavGraphBuilder.loginScreen(onAuthSuccess: () -> Unit) {
	composable(route = LOGIN_ROUTE) {
		LoginScreen(
			onAuthSuccess = onAuthSuccess
		)
	}
}