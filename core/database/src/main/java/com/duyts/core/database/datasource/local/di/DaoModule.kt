package com.duyts.core.database.datasource.local.di

import com.duyts.core.database.datasource.local.AppDatabase
import com.duyts.core.database.datasource.local.dao.TaskDao
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