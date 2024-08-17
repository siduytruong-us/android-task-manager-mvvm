package com.duyts.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.duyts.core.common.ApplicationScope
import com.duyts.core.common.network.AppDispatchers
import com.duyts.core.common.network.Dispatcher
import com.duyts.core.datastore.auth.UserAuthenticationSerializer
import com.duyts.core.datastore.preferences.UserPreferencesSerializer
import com.duyts.tasks.core.datastore.UserAuthentication
import com.duyts.tasks.core.datastore.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreProvider {
	@Provides
	@Singleton
	internal fun provideUserAuthenticationDataStore(
		@ApplicationContext context: Context,
		@Dispatcher(AppDispatchers.IO) ioDispatcher: CoroutineDispatcher,
		@ApplicationScope scope: CoroutineScope,
		userAuthenticationSerializer: UserAuthenticationSerializer,
	): DataStore<UserAuthentication> =
		DataStoreFactory.create(
			serializer = userAuthenticationSerializer,
			scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
		) {
			context.dataStoreFile("user_authentication.pb")
		}

	@Provides
	@Singleton
	internal fun provideUserPreferencesDataStore(
		@ApplicationContext context: Context,
		@Dispatcher(AppDispatchers.IO) ioDispatcher: CoroutineDispatcher,
		@ApplicationScope scope: CoroutineScope,
		userPreferencesSerializer: UserPreferencesSerializer,
	): DataStore<UserPreferences> =
		DataStoreFactory.create(
			serializer = userPreferencesSerializer,
			scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
		) {
			context.dataStoreFile("user_preferences.pb")
		}

}