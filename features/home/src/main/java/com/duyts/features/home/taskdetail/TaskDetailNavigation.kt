package com.duyts.features.home.taskdetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val TASK_DETAIL_BASE_ROUTE = "TASK_DETAIL_BASE_ROUTE"
const val TASK_ID_ARG = "TASK_ID_ARG"
const val TASK_DETAIL_ROUTE = "$TASK_DETAIL_BASE_ROUTE?$TASK_ID_ARG={$TASK_ID_ARG}"
fun NavController.navigateTaskDetailNavigation(taskId: String? = null) =
	navigate(
		TASK_DETAIL_BASE_ROUTE.let {
			if (taskId == null) it else "$it?$TASK_ID_ARG=$taskId"
		}
	)

fun NavGraphBuilder.taskDetail(
	onBack: () -> Unit,
) {
	composable(
		route = TASK_DETAIL_ROUTE,
		arguments = listOf(
			navArgument(TASK_ID_ARG) { type = NavType.StringType; nullable = true }
		)
	) { _ ->
		TaskDetailScreen(onBack = onBack)
	}
}