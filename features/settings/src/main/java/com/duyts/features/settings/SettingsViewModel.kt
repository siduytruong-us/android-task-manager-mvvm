package com.duyts.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duyts.core.data.repository.UserDataRepository
import com.duyts.core.datastore.enum.DarkThemeConfig
import com.duyts.core.datastore.enum.ThemeBrand
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SettingsViewModel @Inject constructor(
	private val userDataRepository: UserDataRepository
) : ViewModel() {


	val settingsUiState: StateFlow<SettingUiState> =
		userDataRepository.userData.map { userData ->
			SettingUiState.Success(
				settings = UserEditableSettings(
					brand = userData.themeBrand,
					darkThemeConfig = userData.darkThemeConfig,
					useDynamicColor = userData.useDynamicColor
				)
			)
		}
			.stateIn(
				scope = viewModelScope,
				started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
				initialValue = SettingUiState.Loading,
			)

	fun updateThemeBrand(themeBrand: ThemeBrand) {
		viewModelScope.launch {
			userDataRepository.setThemeBrand(themeBrand)
		}
	}

	fun updateDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
		viewModelScope.launch {
			userDataRepository.setDarkThemeConfig(darkThemeConfig)
		}
	}

	fun updateDynamicColorPreference(useDynamicColor: Boolean) {
		viewModelScope.launch {
			userDataRepository.setDynamicColor(useDynamicColor)
		}
	}
}

data class UserEditableSettings(
	val brand: ThemeBrand,
	val useDynamicColor: Boolean,
	val darkThemeConfig: DarkThemeConfig,
)

sealed interface SettingUiState {
	data object Loading : SettingUiState
	data class Success(val settings: UserEditableSettings) : SettingUiState
}