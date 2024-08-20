package com.duyts.core.data.repository

import com.duyts.core.data.model.Task
import com.duyts.core.data.model.toEntity
import com.duyts.core.data.model.toFirestoreEntity
import com.duyts.core.data.model.toModel
import com.duyts.core.data.source.local.LocalDataSource
import com.duyts.core.data.source.local.RemoteDataSource
import com.duyts.core.database.datasource.firestore.TaskFirestore
import com.duyts.core.database.datasource.local.dao.TaskDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
	private val taskDao: TaskDao,
	private val taskFirestore: TaskFirestore,
	private val localDataSource: LocalDataSource,
	private val remoteDataSource: RemoteDataSource,
) : TaskRepository {
	override fun observeAll(): Flow<List<Task>> =
		localDataSource.observeTasks()

	override suspend fun insert(task: Task) {
		taskDao.insert(task.toEntity())
		taskFirestore.saveTask(task.toFirestoreEntity())
	}

	override suspend fun update(task: Task) {
		taskDao.update(task.toEntity())
	}

	override suspend fun deleteById(id: String) {
		taskDao.deleteById(id)
	}

	override suspend fun getById(id: String) = taskDao.getById(id)?.toModel()

	fun sync(): Flow<Boolean> = flow {
		val localTasks: Map<String, Task> = localDataSource.getTasks().associateBy { it.id }
		val remoteTasks: Map<String, Task> = remoteDataSource.getAllTasks().associateBy { it.id }

		val finalTasks: Map<String, Task> = (localTasks + remoteTasks)

		localDataSource.addTasks(finalTasks.values.toList().map { it.toEntity() })

		val deleteRemoteTasks = remoteTasks.minus(finalTasks)

		remoteDataSource.updateAndDeleteTasks(
			tasksToUpdate = finalTasks.values.toList().map { it.toFirestoreEntity() },
			tasksToDelete = deleteRemoteTasks.values.toList().map { it.toFirestoreEntity() })

		emit(true)
	}
}