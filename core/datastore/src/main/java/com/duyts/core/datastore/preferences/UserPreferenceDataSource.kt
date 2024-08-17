package com.duyts.core.datastore.preferences

import androidx.datastore.core.DataStore
import com.duyts.core.datastore.enum.DarkThemeConfig
import com.duyts.core.datastore.enum.ThemeBrand
import com.duyts.core.datastore.model.UserData
import com.duyts.tasks.core.datastore.DarkThemeConfigProto
import com.duyts.tasks.core.datastore.ThemeBrandProto
import com.duyts.tasks.core.datastore.UserPreferences
import com.duyts.tasks.core.datastore.copy

import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferenceDataSource @Inject constructor(
	private val userPreferences: DataStore<UserPreferences>
) {
	val userData = userPreferences.data.map { userPref ->
		UserData(
			useDynamicColor = userPref.useDynamicColor,
			themeBrand = when (userPref.themeBrand) {
				null,
				ThemeBrandProto.THEME_BRAND_UNSPECIFIED,
				ThemeBrandProto.UNRECOGNIZED,
				ThemeBrandProto.THEME_BRAND_DEFAULT,
				-> ThemeBrand.DEFAULT

				ThemeBrandProto.THEME_BRAND_ANDROID -> ThemeBrand.ANDROID
			},
			darkThemeConfig = when (userPref.darkThemeConfig) {
				null,
				DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
				DarkThemeConfigProto.UNRECOGNIZED,
				DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM,
				->
					DarkThemeConfig.FOLLOW_SYSTEM

				DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT ->
					DarkThemeConfig.LIGHT

				DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
			}
		)
	}


	suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
		userPreferences.updateData {
			it.copy { this.useDynamicColor = useDynamicColor }
		}
	}

	suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
		userPreferences.updateData {
			it.copy {
				this.darkThemeConfig = when (darkThemeConfig) {
					DarkThemeConfig.FOLLOW_SYSTEM ->
						DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM

					DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
					DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
				}
			}
		}
	}

	suspend fun setThemeBrand(themeBrand: ThemeBrand) {
		userPreferences.updateData {
			it.copy {
				this.themeBrand = when (themeBrand) {
					ThemeBrand.DEFAULT -> ThemeBrandProto.THEME_BRAND_DEFAULT
					ThemeBrand.ANDROID -> ThemeBrandProto.THEME_BRAND_ANDROID
				}
			}
		}
	}
}