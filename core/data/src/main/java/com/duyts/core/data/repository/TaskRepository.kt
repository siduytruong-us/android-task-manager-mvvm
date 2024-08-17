package com.duyts.core.data.repository

import com.duyts.core.data.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
	fun observeAll(): Flow<List<Task>>

	suspend fun insert(task: Task)
	suspend fun update(task: Task)
	suspend fun deleteById(id: String)
	suspend fun getById(id: String): Task?
}