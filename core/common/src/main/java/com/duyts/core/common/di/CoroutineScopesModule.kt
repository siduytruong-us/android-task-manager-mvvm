package com.duyts.core.common.di

import com.duyts.core.common.ApplicationScope
import com.duyts.core.common.BackgroundScope
import com.duyts.core.common.network.AppDispatchers
import com.duyts.core.common.network.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CoroutineScopesModule {
	@Provides
	@Singleton
	@ApplicationScope
	fun provideApplicationScope(
		@Dispatcher(AppDispatchers.Default) dispatcher: CoroutineDispatcher
	): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

	@Provides
	@Singleton
	@BackgroundScope
	fun provideBackgroundScope(
		@Dispatcher(AppDispatchers.IO) dispatcher: CoroutineDispatcher
	): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

}