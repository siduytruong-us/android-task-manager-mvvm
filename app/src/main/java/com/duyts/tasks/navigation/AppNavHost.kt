package com.duyts.tasks.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.duyts.core.design.navigation.TopLevelDestination
import com.duyts.features.auth.loginScreen
import com.duyts.features.auth.navigateToLoginScreen
import com.duyts.features.home.taskdetail.navigateTaskDetailNavigation
import com.duyts.features.home.taskdetail.taskDetail
import com.duyts.features.splash.SPLASH_ROUTE
import com.duyts.features.splash.splashScreen
import com.duyts.tasks.AppState
import com.duyts.features.home.ui.home.homeScreen
import com.duyts.features.profile.profileScreen

@Composable
fun AppNavHost(
	modifier: Modifier = Modifier,
	appState: AppState,
	startDestination: String = SPLASH_ROUTE,
) {
	val navController = appState.navController
	NavHost(navController = navController, startDestination = startDestination) {
		splashScreen(
			onAuthenticate = { isAuthenticated ->
				if (isAuthenticated) {
					appState.navigateToTopLevelDestination(TopLevelDestination.HOME)
				} else {
					navController.navigateToLoginScreen()
				}
			}
		)

		homeScreen(
			onAddTask = navController::navigateTaskDetailNavigation,
			onTaskClick = navController::navigateTaskDetailNavigation
		)

		taskDetail(
			onBack = navController::popBackStack
		)

		profileScreen(
			onLogout = navController::navigateToLoginScreen,
		)

		loginScreen(
			onAuthSuccess = { appState.navigateToTopLevelDestination(TopLevelDestination.HOME) }
		)
	}
}