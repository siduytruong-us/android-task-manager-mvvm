package com.duyts.features.home.ui.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val HOME_SCREEN_ROUTE = "HOME_SCREEN_ROUTE"

fun NavController.navigateToHomeScreen(
	navOptions: NavOptions
) = navigate(HOME_SCREEN_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen(
	onAddTask: () -> Unit = {},
	onTaskClick: (taskId: String?) -> Unit = {}
) {
	composable(route = HOME_SCREEN_ROUTE) {
		HomeScreen(onAddTask = onAddTask, onTaskClick = onTaskClick)
	}
}
