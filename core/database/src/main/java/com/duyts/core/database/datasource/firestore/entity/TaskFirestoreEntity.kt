package com.duyts.core.database.datasource.firestore.entity


data class TaskFirestoreEntity(
	val id: String = "",
	val title: String = "",
	val description: String = "",
	val isCompleted: Boolean = false,
)