package com.duyts.core.data.source.local

import com.duyts.core.data.model.Task
import com.duyts.core.database.datasource.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
	suspend fun addTask(task: TaskEntity)
	suspend fun addTasks(tasks: List<TaskEntity>)
	suspend fun getTasks(): List<Task>
	fun observeTasks(): Flow<List<Task>>
}