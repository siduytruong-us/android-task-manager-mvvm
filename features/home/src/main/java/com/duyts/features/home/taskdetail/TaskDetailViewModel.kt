package com.duyts.features.home.taskdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duyts.core.data.model.Task
import com.duyts.core.data.repository.TaskRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel
@Inject constructor(
	private val taskRepository: TaskRepositoryImpl,
	savedStateHandle: SavedStateHandle,
) : ViewModel() {
	private val taskId: String? = savedStateHandle[TASK_ID_ARG]
	private val _uiState = MutableStateFlow(TaskDetailUiState())
	val uiState: StateFlow<TaskDetailUiState> = _uiState

	init {
		loadTask()
	}

	private fun loadTask() = viewModelScope.launch {
		if (taskId == null) return@launch
		taskRepository.getById(taskId)?.let { task ->
			_uiState.update {
				it.copy(id = taskId, title = task.title, description = task.description)
			}
		}
	}

	fun onTitleChange(title: String) {
		_uiState.update { it.copy(title = title) }
	}

	fun onDescriptionChange(description: String) {
		_uiState.update { it.copy(description = description) }
	}

	fun saveTask() {
		if (taskId == null) {
			createTask()
		} else {
			updateTask()
		}
	}

	private fun createTask() = viewModelScope.launch {
		val (_, title, description, isLoading) = _uiState.value
		if (isLoading) return@launch
		taskRepository.insert(Task(title = title, description = description))
		_uiState.update { it.copy(isTaskSaved = true) }
	}

	private fun updateTask() = viewModelScope.launch {
		val (_, title, description, isLoading) = _uiState.value
		if (isLoading || taskId == null) return@launch
		taskRepository.update(Task(id = taskId, title = title, description = description))
		_uiState.update { it.copy(isTaskSaved = true) }
	}

	fun deleteTask() = viewModelScope.launch {
		val (_, _, _, isLoading) = _uiState.value
		if (isLoading || taskId == null) return@launch
		taskRepository.deleteById(taskId)
		_uiState.update { it.copy(isTaskDeleted = true) }
	}
}

data class TaskDetailUiState(
	var id: String? = null,
	var title: String? = null,
	var description: String? = null,
	var isLoading: Boolean = false,
	var isTaskSaved: Boolean = false,
	var isTaskDeleted: Boolean = false
)