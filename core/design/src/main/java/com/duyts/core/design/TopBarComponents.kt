@file:OptIn(ExperimentalMaterial3Api::class)

package com.duyts.core.design

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TasksTopAppBar(
	onFilterAllTasks: () -> Unit,
	onFilterActiveTasks: () -> Unit,
	onFilterCompletedTasks: () -> Unit
) {
	TopAppBar(
		modifier = Modifier.fillMaxWidth(),
		title = { Text(text = "ToDo") },
		actions = {
			FilterTasksMenu(
				onFilterAllTasks,
				onFilterActiveTasks,
				onFilterCompletedTasks
			)
		}
	)
}

@Composable
private fun FilterTasksMenu(
	onFilterAllTasks: () -> Unit,
	onFilterActiveTasks: () -> Unit,
	onFilterCompletedTasks: () -> Unit
) {
	TopAppBarDropdownMenu(
		iconContent = {
			Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
		}
	) { closeMenu ->
		DropdownMenuItem(text = {
			Text(text = "All")
		}, onClick = { onFilterAllTasks(); closeMenu() })
		DropdownMenuItem(text = {
			Text(text = "Active")
		}, onClick = { onFilterActiveTasks(); closeMenu() })
		DropdownMenuItem(text = {
			Text(text = "Completed")
		}, onClick = { onFilterCompletedTasks(); closeMenu() })
	}

}

@Composable
fun TopAppBarDropdownMenu(
	iconContent: @Composable () -> Unit,
	content: @Composable ColumnScope.(() -> Unit) -> Unit
) {
	var expanded by remember { mutableStateOf(false) }
	Box(modifier = Modifier.wrapContentSize(align = Alignment.TopEnd)) {
		IconButton(onClick = { expanded = !expanded }) {
			iconContent()
		}
		DropdownMenu(
			expanded = expanded,
			onDismissRequest = { expanded = false },
			modifier = Modifier.wrapContentSize(
				Alignment.TopEnd
			)
		) {
			content { expanded = !expanded }
		}
	}
}
