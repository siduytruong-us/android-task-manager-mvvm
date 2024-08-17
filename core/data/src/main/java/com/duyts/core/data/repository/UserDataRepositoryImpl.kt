package com.duyts.core.data.repository

import com.duyts.core.datastore.enum.DarkThemeConfig
import com.duyts.core.datastore.enum.ThemeBrand
import com.duyts.core.datastore.model.UserData
import com.duyts.core.datastore.preferences.UserPreferenceDataSource

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
	private val userPrefDataSource: UserPreferenceDataSource,
) : UserDataRepository {
	override val userData: Flow<UserData> = userPrefDataSource.userData

	override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
		userPrefDataSource.setDarkThemeConfig(darkThemeConfig)
	}

	override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
		userPrefDataSource.setThemeBrand(themeBrand)
	}

	override suspend fun setDynamicColor(useDynamicColor: Boolean) {
		userPrefDataSource.setDynamicColorPreference(useDynamicColor)
	}
}