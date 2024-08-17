package com.duyts.tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.duyts.core.datastore.enum.DarkThemeConfig
import com.duyts.core.datastore.enum.ThemeBrand

import com.duyts.tasks.ui.MainApp
import com.duyts.core.design.theme.TasksTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	val viewModel: MainActivityViewModel by viewModels()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

		// Update the uiState
		lifecycleScope.launch {
			lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.uiState
					.onEach { uiState = it }.collect()
			}
		}
		setContent {
			val darkTheme = shouldUseDarkTheme(uiState)
			val appState = rememberAppState()
			TasksTheme(
				darkTheme = darkTheme,
				androidTheme = shouldUseAndroidTheme(uiState),
				disableDynamicTheming = shouldDisableDynamicTheming(uiState),
			) {
				MainApp(appState)
			}
		}
	}
}

@Composable
private fun shouldUseDarkTheme(
	uiState: MainActivityUiState,
): Boolean = when (uiState) {
	MainActivityUiState.Loading -> isSystemInDarkTheme()
	is MainActivityUiState.Success -> when (uiState.userData.darkThemeConfig) {
		DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
		DarkThemeConfig.LIGHT -> false
		DarkThemeConfig.DARK -> true
	}
}

@Composable
private fun shouldUseAndroidTheme(
	uiState: MainActivityUiState,
): Boolean = when (uiState) {
	MainActivityUiState.Loading -> false
	is MainActivityUiState.Success -> when (uiState.userData.themeBrand) {
		ThemeBrand.DEFAULT -> false
		ThemeBrand.ANDROID -> true
	}
}

@Composable
private fun shouldDisableDynamicTheming(
	uiState: MainActivityUiState,
): Boolean = when (uiState) {
	MainActivityUiState.Loading -> false
	is MainActivityUiState.Success -> !uiState.userData.useDynamicColor
}
