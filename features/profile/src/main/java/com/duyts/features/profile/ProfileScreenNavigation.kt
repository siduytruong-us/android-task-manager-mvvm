package com.duyts.features.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val PROFILE_SCREEN_ROUTE = "PROFILE_SCREEN_ROUTE"

fun NavController.navigateToProfileScreen(navOptions: NavOptions) {
	navigate(PROFILE_SCREEN_ROUTE, navOptions)
}

fun NavGraphBuilder.profileScreen(
	onLogout: (() -> Unit),
) {
	composable(PROFILE_SCREEN_ROUTE) {
		ProfileScreen(
			onLogout = onLogout
		)
	}
}