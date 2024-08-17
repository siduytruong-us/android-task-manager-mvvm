package com.duyts.features.home.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.duyts.core.data.model.Task
import com.duyts.core.design.TasksTopAppBar
import com.duyts.features.home.TasksFilterType

@Composable
fun HomeScreen(
	tasksViewModel: HomeScreenViewModel = hiltViewModel(),
	onAddTask: () -> Unit = {},
	onTaskClick: (taskId: String?) -> Unit = {}
) {
	val uiState by tasksViewModel.taskUiState.collectAsState()
	Scaffold(
		modifier = Modifier.fillMaxSize(),
		topBar = {
			TasksTopAppBar(
				onFilterAllTasks = { tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS) },
				onFilterActiveTasks = { tasksViewModel.setFiltering(TasksFilterType.ACTIVE_TASKS) },
				onFilterCompletedTasks = { tasksViewModel.setFiltering(TasksFilterType.COMPLETED_TASKS) }
			)
		},
		floatingActionButton = {
			FloatingActionButton(
				modifier = Modifier.padding(20.dp),
				onClick = { onAddTask() }
			) {
				Icon(imageVector = Icons.Filled.Add, contentDescription = null)
			}
		},
		bottomBar = {

		}
	) { paddingValues ->
		when (uiState) {
			is HomeUiState.Success -> {
				LazyColumn(
					modifier = Modifier
						.fillMaxSize()
						.padding(paddingValues)
				) {
					val tasks = (uiState as HomeUiState.Success).tasks
					items(tasks) {
						TaskItem(
							it, onTaskClick = onTaskClick,
							onTaskCheckedChange = tasksViewModel::completeTask
						)
					}
				}
			}

			else -> {

			}
		}

	}
}


@Composable
fun TaskItem(
	task: Task,
	onTaskClick: (String?) -> Unit = {},
	onTaskCheckedChange: (Task, Boolean) -> Unit,
) {
	Card(
		modifier = Modifier
			.fillMaxSize()
			.padding(8.dp, 8.dp, 8.dp, 8.dp)
			.clickable { onTaskClick(task.id) },
		elevation = CardDefaults.cardElevation(4.dp)
	) {
		Row(
			modifier = Modifier.padding(10.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			Checkbox(
				checked = task.isCompleted,
				onCheckedChange = { onTaskCheckedChange(task, it) }
			)
			Text(
				text = task.title.orEmpty(),
				style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
				maxLines = 1,
				textDecoration = if (task.isCompleted) {
					TextDecoration.LineThrough
				} else {
					null
				}
			)
		}
	}
}
