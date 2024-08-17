package com.duyts.core.design.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.duyts.core.design.AppNavigationDefaults

@Composable
fun RowScope.AppNavigationBarItem(
	selected: Boolean,
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	icon: @Composable () -> Unit,
	selectedIcon: @Composable () -> Unit = icon
) {
	NavigationBarItem(
		selected = selected,
		onClick = onClick,
		icon = if (selected) selectedIcon else icon,
		modifier = modifier,
		enabled = enabled,
		colors = NavigationBarItemDefaults.colors(
			selectedIconColor = AppNavigationDefaults.navigationSelectedItemColor(),
			unselectedIconColor = AppNavigationDefaults.navigationContentColor(),
			selectedTextColor = AppNavigationDefaults.navigationSelectedItemColor(),
			unselectedTextColor = AppNavigationDefaults.navigationContentColor(),
			indicatorColor = AppNavigationDefaults.navigationIndicatorColor()

		)
	)
}