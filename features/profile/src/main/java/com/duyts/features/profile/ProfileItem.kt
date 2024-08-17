package com.duyts.features.profile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class ProfileItem(
	val title: String,
	val icon: ImageVector,
	val color: Color
) {
	EDIT_PROFILE("Edit Profile", Icons.Filled.Edit, Color.Blue),
	SETTING("Settings", Icons.Filled.Settings, Color.Blue),
	LOG_OUT("Logout", Icons.AutoMirrored.Filled.ExitToApp, Color.Red)
}