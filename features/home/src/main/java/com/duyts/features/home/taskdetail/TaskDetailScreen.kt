@file:OptIn(ExperimentalMaterial3Api::class)

package com.duyts.features.home.taskdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@Composable
fun TaskDetailScreen(
	onBack: () -> Unit,
	taskDetailViewModel: TaskDetailViewModel = hiltViewModel(),
) {
	val state = taskDetailViewModel.uiState.collectAsState()
	val scroll = rememberScrollState()
	val snackBarHostState = remember { SnackbarHostState() }
	val scope = rememberCoroutineScope()

	val (taskId, title, description, isLoading, isTaskSaved, isTaskDeleted) = state.value
	LaunchedEffect(isTaskSaved, isTaskDeleted) {
		if (isTaskSaved || isTaskDeleted) {
			onBack.invoke()
		}

		if (isTaskDeleted) {
			scope.launch {
				val result = snackBarHostState.showSnackbar("Task deleted!", "Undo")
				when (result) {
					SnackbarResult.ActionPerformed -> {

					}
					SnackbarResult.Dismissed -> {

					}
				}
			}
		}
	}
	Scaffold(
		snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
		modifier = Modifier.fillMaxSize(),
		topBar = {
			TaskDetailTopAppBar(
				onBack = onBack,
				onSave = taskDetailViewModel::saveTask
			)
		}) { paddingValues ->
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
		) {
			if (isLoading) CircularProgressIndicator()
			Column(
				modifier = Modifier
					.fillMaxSize()
					.padding(10.dp)
					.verticalScroll(scroll),
				horizontalAlignment = Alignment.CenterHorizontally

			) {
				WishTextField(
					value = title.orEmpty(), placeHolder = "Title",
					onValueChange = {
						taskDetailViewModel.onTitleChange(it)
					},
					textStyle = TextStyle(
						fontSize = 24.sp, fontWeight = FontWeight.Bold
					),
				)
				WishTextField(
					value = description.orEmpty(),
					placeHolder = "Description",
					onValueChange = {
						taskDetailViewModel.onDescriptionChange(it)
					},
					textStyle = TextStyle(
						fontSize = 20.sp
					),
				)

				if (!taskId.isNullOrEmpty()) {
					Button(
						onClick = taskDetailViewModel::deleteTask,
						colors = ButtonDefaults.buttonColors(
							containerColor = Color.Red,
							contentColor = Color.White
						),
					) {
						Text(text = "Delete task!")
					}
				}
			}
		}
	}

}

@Composable
fun TaskDetailTopAppBar(
	onSave: () -> Unit,
	onBack: () -> Unit,
) {
	TopAppBar(title = { /*TODO*/ }, actions = {
		IconButton(onClick = { onSave.invoke() }) {
			Icon(imageVector = Icons.Default.Check, contentDescription = null)
		}
	}, navigationIcon = {
		IconButton(onClick = { onBack.invoke() }) {
			Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
		}
	})
}


@Composable
fun WishTextField(
	modifier: Modifier = Modifier,
	value: String,
	onValueChange: (String) -> Unit,
	placeHolder: String,
	textStyle: TextStyle = TextStyle()
) {
	OutlinedTextField(
		modifier = modifier.fillMaxWidth(),
		value = value,
		onValueChange = onValueChange,
		placeholder = {
			Text(text = placeHolder, style = textStyle)
		},
		colors = OutlinedTextFieldDefaults.colors(
			focusedBorderColor = Color.Transparent,
			unfocusedBorderColor = Color.Transparent,
		),
		textStyle = textStyle
	)
}
