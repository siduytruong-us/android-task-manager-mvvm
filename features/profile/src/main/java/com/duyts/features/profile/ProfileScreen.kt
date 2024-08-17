package com.duyts.features.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.duyts.features.settings.SettingsDialog
import com.duyts.features.profile.ProfileItem.EDIT_PROFILE
import com.duyts.features.profile.ProfileItem.LOG_OUT
import com.duyts.features.profile.ProfileItem.SETTING


@Composable
fun ProfileScreen(
	viewModel: ProfileScreenViewModel = hiltViewModel(),
	onNavigateToEditProfile: (() -> Unit)? = null,
	onLogout: (() -> Unit)? = null,
) {
	val state = viewModel.state.collectAsState().value
	var showSettingsDialog by rememberSaveable { mutableStateOf(false) }

	if (showSettingsDialog) {
		SettingsDialog(onDismiss = { showSettingsDialog = false })
	}
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(top = 30.dp)
	) {
		ProfileHeader(state)
		Spacer(modifier = Modifier.height(20.dp))
		ProfileItems(
			onClick = { profileItem ->
				when (profileItem) {
					EDIT_PROFILE -> onNavigateToEditProfile?.invoke()
					SETTING -> showSettingsDialog = true
					LOG_OUT -> {
						viewModel.logout()
						onLogout?.invoke()
					}
				}
			}
		)
	}
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ProfileHeader(state: ProfileScreenUiState) {
	when (state) {

		is ProfileScreenUiState.Success -> {
			val userData = state.userData
			Column(
				modifier = Modifier.fillMaxWidth(),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				GlideImage(
					model = userData.photoUrl,
					loading = placeholder(rememberVectorPainter(image = Icons.Default.Person)),
					contentDescription = null,
					modifier = Modifier.size(100.dp).clip(CircleShape)
				)
				Text(text = userData.displayName)
				Text(text = userData.email)
			}
		}

		ProfileScreenUiState.Loading -> {
			//skeleton here
		}
	}

}

@Composable
private fun ProfileItems(onClick: (ProfileItem) -> Unit) {
	Column(
		modifier = Modifier.fillMaxWidth(),
		verticalArrangement = Arrangement.Center
	) {
		LazyColumn {
			items(ProfileItem.entries, key = { item -> item.hashCode() }) { item ->
				ProfileItemComponent(item, onClick)
			}
		}
	}
}


@Composable
private fun ProfileItemComponent(
	item: ProfileItem, onClick: (ProfileItem) -> Unit
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.clickable { onClick.invoke(item) }
			.padding(10.dp),
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically
		) {
			Image(
				imageVector = item.icon, contentDescription = null,
				modifier = Modifier
					.size(30.dp)
					.padding(4.dp),
				colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
			)
			Text(
				text = item.title,
				style = TextStyle.Default
			)
		}
		Image(
			imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null,
			modifier = Modifier.size(30.dp)
		)
	}
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenReview() {
	ProfileScreen()
}