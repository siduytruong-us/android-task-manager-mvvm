package com.duyts.core.data.source.local

import com.duyts.core.data.model.Task
import com.duyts.core.database.datasource.firestore.entity.TaskFirestoreEntity

interface RemoteDataSource {
	suspend fun getAllTasks(): List<Task>
	suspend fun updateAndDeleteTasks(
		tasksToUpdate: List<TaskFirestoreEntity>,
		tasksToDelete: List<TaskFirestoreEntity>,
	)
}