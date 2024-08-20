package com.duyts.core.database.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.duyts.core.database.datasource.local.dao.TaskDao
import com.duyts.core.database.datasource.local.entity.TaskEntity

@Database(
	entities = [TaskEntity::class], version = 1
)
internal abstract class AppDatabase : RoomDatabase() {
	abstract  fun taskDao(): TaskDao
}