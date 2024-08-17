package com.duyts.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.duyts.tasks.datasource.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
	@Query("SELECT * FROM task")
	fun observeAll(): Flow<List<TaskEntity>>

	@Query("SELECT * FROM task WHERE id=:id")
	suspend fun getById(id: String): TaskEntity?

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insert(taskEntity: TaskEntity)

	@Query("DELETE FROM task WHERE id=:id")
	suspend fun deleteById(id: String)

	@Update
	suspend fun update(taskEntity: TaskEntity)
}
