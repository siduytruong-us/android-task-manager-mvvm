package com.duyts.core.data.source.local

import com.duyts.core.data.model.Task
import com.duyts.core.data.model.toEntity
import com.duyts.core.data.model.toModel
import com.duyts.core.database.datasource.local.dao.TaskDao
import com.duyts.core.database.datasource.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
	private val taskDao: TaskDao,
) : LocalDataSource {
	override suspend fun addTask(task: TaskEntity) {
		taskDao.insert(task)
	}

	override suspend fun addTasks(tasks: List<TaskEntity>) {
		taskDao.insertAll(tasks)
	}
	override suspend fun getTasks(): List<Task> = taskDao.getTasks().map { it.toModel() }

	override fun observeTasks(): Flow<List<Task>> =
		taskDao.observeAll().distinctUntilChanged().map { entities ->
			entities.map { it.toModel() }
		}
}