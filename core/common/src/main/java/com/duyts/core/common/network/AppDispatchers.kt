package com.duyts.core.common.network

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatchers: AppDispatchers)

enum class AppDispatchers {
	Default,
	IO,
}
