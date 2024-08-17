package com.duyts.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.duyts.core.database.dao.TaskDao
import com.duyts.tasks.datasource.entity.TaskEntity

@Database(
	entities = [TaskEntity::class], version = 1
)
internal abstract class AppDatabase : RoomDatabase() {
	abstract  fun taskDao(): TaskDao
}