package com.duyts.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duyts.core.data.repository.AuthenticateRepository
import com.duyts.core.datastore.model.UserAuthData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
	private val authRepository: AuthenticateRepository
) : ViewModel() {


	private val _state = authRepository.getUserData().map {
		ProfileScreenUiState.Success(it)
	}
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5_000),
			initialValue = ProfileScreenUiState.Loading

		)
	val state: StateFlow<ProfileScreenUiState> = _state

	fun logout() = viewModelScope.launch {
		authRepository.logout()
	}
}


sealed class ProfileScreenUiState {
	data class Success(
		val userData: UserAuthData
	) : ProfileScreenUiState()

	data object Loading : ProfileScreenUiState()
}