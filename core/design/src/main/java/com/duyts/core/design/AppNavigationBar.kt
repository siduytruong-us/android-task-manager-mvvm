package com.duyts.core.design

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppNavigationBar(
	modifier: Modifier = Modifier,
	content: @Composable RowScope.() -> Unit,
) {
	NavigationBar(
		modifier = modifier,
		contentColor = AppNavigationDefaults.navigationContentColor(),
		tonalElevation = 0.dp,
		content = content
	)
}


/**
 * Now in Android navigation default values.
 */
object AppNavigationDefaults {
	@Composable
	fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

	@Composable
	fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

	@Composable
	fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}
