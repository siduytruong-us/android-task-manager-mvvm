package com.duyts.core.data.repository


import com.duyts.core.datastore.enum.DarkThemeConfig
import com.duyts.core.datastore.enum.ThemeBrand
import com.duyts.core.datastore.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
	val userData: Flow<UserData>

	suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)
	suspend fun setThemeBrand(themeBrand: ThemeBrand)
	suspend fun setDynamicColor(useDynamicColor: Boolean)
}