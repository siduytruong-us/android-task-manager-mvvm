package com.duyts.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duyts.core.data.repository.AuthenticateRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
	authenticateRepository: AuthenticateRepositoryImpl
) : ViewModel() {
	private val _uiState = authenticateRepository.isAuthenticated().map {
		SplashScreenUiState.Success(it)
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(5_000),
		initialValue = SplashScreenUiState.Loading
	)

	val uiState = _uiState
}

sealed interface SplashScreenUiState {
	data object Loading : SplashScreenUiState
	data class Success(val isAuthenticated: Boolean = false) : SplashScreenUiState
}