package com.duyts.core.datastore.model

import com.duyts.core.datastore.enum.DarkThemeConfig
import com.duyts.core.datastore.enum.ThemeBrand


data class UserData(
	val themeBrand: ThemeBrand,
	val darkThemeConfig: DarkThemeConfig,
	val useDynamicColor: Boolean,
)

data class UserAuthData(
	val email: String,
	val passwordHash: String,
	val authToken: String,
	val displayName: String,
	val photoUrl: String,
)