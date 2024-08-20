package com.duyts.core.data.source.local

import com.duyts.core.data.model.Task
import com.duyts.core.data.model.toModel
import com.duyts.core.database.datasource.firestore.TaskFirestore
import com.duyts.core.database.datasource.firestore.entity.TaskFirestoreEntity
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
	private val firestore: TaskFirestore,
) : RemoteDataSource {

	override suspend fun getAllTasks(): List<Task> =
		firestore.getTasks().first().map { it.toModel() }

	override suspend fun updateAndDeleteTasks(
		tasksToUpdate: List<TaskFirestoreEntity>,
		tasksToDelete: List<TaskFirestoreEntity>,
	) {
		firestore.updateAndDeleteTasks(tasksToDelete = tasksToDelete, tasksToUpdate = tasksToUpdate)
	}
}