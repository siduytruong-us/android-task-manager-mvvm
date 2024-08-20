package com.duyts.core.data.model

import com.duyts.core.database.datasource.firestore.entity.TaskFirestoreEntity
import com.duyts.core.database.datasource.local.entity.TaskEntity
import java.util.UUID

data class Task(
	val id: String = UUID.randomUUID().toString(),
	val title: String? = null,
	val description: String? = null,
	val isCompleted: Boolean = false,
)

fun TaskEntity.toModel() = Task(id, title, description, isCompleted)
fun Task.toEntity() = TaskEntity(id, title.orEmpty(), description.orEmpty(), isCompleted)

fun Task.toFirestoreEntity() = TaskFirestoreEntity(id, title.orEmpty(), description.orEmpty(), isCompleted)
fun TaskFirestoreEntity.toModel() = Task(id, title, description, isCompleted)
