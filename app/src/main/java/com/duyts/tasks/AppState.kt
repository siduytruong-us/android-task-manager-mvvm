package com.duyts.tasks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.duyts.features.home.ui.home.HOME_SCREEN_ROUTE
import com.duyts.features.home.ui.home.navigateToHomeScreen
import com.duyts.features.profile.PROFILE_SCREEN_ROUTE
import com.duyts.features.profile.navigateToProfileScreen
import com.duyts.core.design.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
	navController: NavHostController = rememberNavController(),
	coroutineScope: CoroutineScope = rememberCoroutineScope(),

	): AppState {
	return remember(
		navController,
		coroutineScope
	) {
		AppState(navController = navController)
	}
}

@Stable
class AppState(
	val navController: NavHostController,
) {
	val currentDestination: NavDestination?
		@Composable get() = navController.currentBackStackEntryAsState().value?.destination

	val currentTopLevelDestination: TopLevelDestination?
		@Composable get() = when (currentDestination?.route) {
			HOME_SCREEN_ROUTE -> TopLevelDestination.HOME
			PROFILE_SCREEN_ROUTE -> TopLevelDestination.PROFILE
			else -> null
		}

	val topLevelDestination: List<TopLevelDestination> = TopLevelDestination.entries

	fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
		val topLevelNavOptions = navOptions {
			popUpTo(navController.graph.findStartDestination().id) {
				saveState = true
			}

			launchSingleTop = true
			restoreState = true
		}
		when (topLevelDestination) {
			TopLevelDestination.HOME -> navController.navigateToHomeScreen(topLevelNavOptions)
			TopLevelDestination.PROFILE -> navController.navigateToProfileScreen(topLevelNavOptions)
		}
	}
}
