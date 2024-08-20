package com.duyts.core.data.di

import com.duyts.core.common.BackgroundScope
import com.duyts.core.data.repository.AuthenticateRepository
import com.duyts.core.data.repository.AuthenticateRepositoryImpl
import com.duyts.core.data.repository.TaskRepository
import com.duyts.core.data.repository.TaskRepositoryImpl
import com.duyts.core.data.repository.UserDataRepository
import com.duyts.core.data.repository.UserDataRepositoryImpl
import com.duyts.core.data.source.local.LocalDataSource
import com.duyts.core.data.source.local.RemoteDataSource
import com.duyts.core.database.datasource.firestore.TaskFirestore
import com.duyts.core.database.datasource.local.dao.TaskDao
import com.duyts.core.datastore.auth.UserAuthenticationDataSource
import com.duyts.core.datastore.preferences.UserPreferenceDataSource
import com.duyts.core.firebase.AppFirebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(SingletonComponent::class)
object RepositoryProvider {
	@Provides
	fun provideTaskRepository(
		taskDao: TaskDao,
		taskFirestore: TaskFirestore,
		localDataSource: LocalDataSource,
		remoteDataSource: RemoteDataSource,
	): TaskRepository =
		TaskRepositoryImpl(taskDao, taskFirestore, localDataSource, remoteDataSource)

	@Provides
	fun provideAuthenticateRepository(
		appFirebase: AppFirebase,
		userAuthDataSource: UserAuthenticationDataSource,
		@BackgroundScope backgroundScope: CoroutineScope,
	): AuthenticateRepository =
		AuthenticateRepositoryImpl(appFirebase, userAuthDataSource, backgroundScope)

	@Provides
	fun provideUserDataRepository(
		userPrefDataSource: UserPreferenceDataSource,
	): UserDataRepository = UserDataRepositoryImpl(userPrefDataSource)
}