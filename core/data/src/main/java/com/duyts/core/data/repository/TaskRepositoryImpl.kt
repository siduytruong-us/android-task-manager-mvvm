package com.duyts.core.data.repository

import com.duyts.core.data.model.Task
import com.duyts.core.data.model.toEntity
import com.duyts.core.data.model.toModel
import com.duyts.core.database.dao.TaskDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
	private val taskDao: TaskDao
) : TaskRepository {
	override fun observeAll(): Flow<List<Task>> =
		taskDao.observeAll().distinctUntilChanged().map { entities ->
			entities.map { it.toModel() }
		}

	override suspend fun insert(task: Task) {
		taskDao.insert(task.toEntity())
	}

	override suspend fun update(task: Task) {
		taskDao.update(task.toEntity())
	}

	override suspend fun deleteById(id: String) {
		taskDao.deleteById(id)
	}

	override suspend fun getById(id: String) = taskDao.getById(id)?.toModel()
}