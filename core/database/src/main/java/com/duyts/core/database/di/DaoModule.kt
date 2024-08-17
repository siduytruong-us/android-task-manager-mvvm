package com.duyts.core.database.di

import com.duyts.core.database.AppDatabase
import com.duyts.core.database.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
	@Provides
	fun providesTaskDao(
		database: AppDatabase
	): TaskDao = database.taskDao()

}