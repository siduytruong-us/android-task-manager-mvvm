package com.duyts.features.home.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duyts.core.data.model.Task
import com.duyts.core.data.repository.TaskRepositoryImpl
import com.duyts.features.home.TasksFilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TASKS_FILTER_SAVED_STATE_KEY = "TASKS_FILTER_SAVED_STATE_KEY"

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
	private val tasksRepository: TaskRepositoryImpl,
	private val savedStateHandle: SavedStateHandle
) : ViewModel() {
	private val _filterType = savedStateHandle.getStateFlow(
		TASKS_FILTER_SAVED_STATE_KEY,
		TasksFilterType.ALL_TASKS
	)
	private val _tasksUiState =
		combine(tasksRepository.observeAll(), _filterType) { list, type ->
			HomeUiState.Success(filterTask(list, type))
		}.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(),
			initialValue = HomeUiState.Loading
		)
	val taskUiState = _tasksUiState

	private fun filterTask(tasks: List<Task>, filterType: TasksFilterType): List<Task> =
		tasks.filter { task ->
			when (filterType) {
				TasksFilterType.ALL_TASKS -> true
				TasksFilterType.ACTIVE_TASKS -> !task.isCompleted
				TasksFilterType.COMPLETED_TASKS -> task.isCompleted
			}
		}

	fun setFiltering(type: TasksFilterType) {
		savedStateHandle[TASKS_FILTER_SAVED_STATE_KEY] = type
	}

	fun completeTask(task: Task, isChecked: Boolean) = viewModelScope.launch {
		tasksRepository.update(task.copy(isCompleted = isChecked))
	}
}

sealed class HomeUiState {
	data object Error : HomeUiState()
	data object Loading : HomeUiState()
	data class Success(val tasks: List<Task>) : HomeUiState()
}