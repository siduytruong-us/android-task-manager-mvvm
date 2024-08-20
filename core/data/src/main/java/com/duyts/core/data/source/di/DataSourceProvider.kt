package com.duyts.core.data.source.di

import com.duyts.core.data.source.local.LocalDataSource
import com.duyts.core.data.source.local.LocalDataSourceImpl
import com.duyts.core.data.source.local.RemoteDataSource
import com.duyts.core.data.source.local.RemoteDataSourceImpl
import com.duyts.core.database.datasource.firestore.TaskFirestore
import com.duyts.core.database.datasource.local.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceProvider {

	@Provides
	@Singleton
	fun providesLocalDataSource(taskDao: TaskDao): LocalDataSource = LocalDataSourceImpl(taskDao)

	@Provides
	@Singleton
	fun providesFirestoreDataSource(taskFirestore: TaskFirestore): RemoteDataSource =
		RemoteDataSourceImpl(taskFirestore)
}