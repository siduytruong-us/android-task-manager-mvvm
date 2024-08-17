package com.duyts.core.common.di

import com.duyts.core.common.network.AppDispatchers
import com.duyts.core.common.network.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
	@Provides
	@Dispatcher(AppDispatchers.IO)
	fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

	@Provides
	@Dispatcher(AppDispatchers.Default)
	fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}