package com.duyts.tasks.datasource.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("task")
data class TaskEntity(
	@PrimaryKey
	val id: String,
	val title: String,
	val description: String,
	@ColumnInfo(name = "is_completed")
	val isCompleted: Boolean = false,
)