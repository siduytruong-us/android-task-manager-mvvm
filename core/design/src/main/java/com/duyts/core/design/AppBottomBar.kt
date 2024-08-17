package com.duyts.core.design

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.duyts.core.design.navigation.AppNavigationBarItem
import com.duyts.core.design.navigation.TopLevelDestination

@Composable
fun AppBottomBar(
	destinations: List<TopLevelDestination>,
	onNavigateToDestination: (TopLevelDestination) -> Unit,
	currentDestination: NavDestination?,
	modifier: Modifier,
) {
	AppNavigationBar(
		modifier = modifier
	) {
		destinations.forEach { dest ->
			val selected = currentDestination.isTopLevelDestinationInHierarchy(dest)
			AppNavigationBarItem(
				selected = selected,
				selectedIcon = {
					Icon(imageVector = dest.selectedIcon, contentDescription = null)
				},
				icon = {
					Icon(imageVector = dest.unselectedIcon, contentDescription = null)
				},
				onClick = { onNavigateToDestination.invoke(dest) },
				modifier = modifier
			)
		}
	}
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(dest: TopLevelDestination) =
	this?.hierarchy?.any {
		it.route?.contains(dest.name, true) ?: false
	} ?: false