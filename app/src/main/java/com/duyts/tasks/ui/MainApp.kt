package com.duyts.tasks.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.duyts.core.common.extension.isNotNull
import com.duyts.core.design.AppBottomBar
import com.duyts.tasks.AppState
import com.duyts.tasks.navigation.AppNavHost

@Composable
fun MainApp(appState: AppState) {
	val snackBarHostState = remember { SnackbarHostState() }
	Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
		MainAppUi(appState, snackBarHostState)
	}
}

@Composable
fun MainAppUi(appState: AppState, snackBarHostState: SnackbarHostState) {

	val shouldShowBottomBar = appState.currentTopLevelDestination.isNotNull()
	Scaffold(
		containerColor = Color.Transparent,
		contentColor = MaterialTheme.colorScheme.onBackground,
		contentWindowInsets = WindowInsets(0, 0, 0, 0),
		snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
		bottomBar = {
			if (shouldShowBottomBar) {
				AppBottomBar(
					destinations = appState.topLevelDestination,
					onNavigateToDestination = appState::navigateToTopLevelDestination,
					currentDestination = appState.currentDestination,
					modifier = Modifier.testTag("AppBottomBar")
				)
			}
		}
	) { paddingValues ->
		Row(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
				.consumeWindowInsets(paddingValues)
				.windowInsetsPadding(
					WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
				)
		) {
			Column(modifier = Modifier.fillMaxSize()) {
				val dest = appState.currentTopLevelDestination
				AppNavHost(appState = appState)
			}
		}
	}
}
