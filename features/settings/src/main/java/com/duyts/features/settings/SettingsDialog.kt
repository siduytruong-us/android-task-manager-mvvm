package com.duyts.features.settings

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duyts.core.datastore.enum.DarkThemeConfig
import com.duyts.core.datastore.enum.ThemeBrand
import com.duyts.core.design.supportsDynamicTheming

@Composable
fun SettingsDialog(
	onDismiss: () -> Unit,
	viewModel: SettingsViewModel = hiltViewModel()
) {
	val state by viewModel.settingsUiState.collectAsStateWithLifecycle()
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
		SettingsDialog(
			state = state, onDismiss = onDismiss,
			onChangeThemeBrand = viewModel::updateThemeBrand,
			onChangeDynamicColorPreference = viewModel::updateDynamicColorPreference,
			onChangeDarkThemeConfig = viewModel::updateDarkThemeConfig,
		)
	}
}

@RequiresApi(Build.VERSION_CODES.HONEYCOMB_MR2)
@Composable
fun SettingsDialog(
	state: SettingUiState,
	onDismiss: () -> Unit,
	onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
	onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
	onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
	supportDynamicColor: Boolean = supportsDynamicTheming(),
) {
	val configuration = LocalConfiguration.current
	AlertDialog(
		properties = DialogProperties(usePlatformDefaultWidth = false),
		modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
		title = {
			Text(
				text = stringResource(R.string.alert_dialog_title),
				style = MaterialTheme.typography.titleLarge,
			)
		},
		text = {
			HorizontalDivider()
			Column(Modifier.verticalScroll(rememberScrollState())) {
				when (state) {
					SettingUiState.Loading -> {
						Text(
							text = stringResource(R.string.alert_loading),
							modifier = Modifier.padding(vertical = 16.dp),
						)
					}

					is SettingUiState.Success -> {
						SettingsPanel(
							settings = state.settings,
							supportDynamicColor = supportDynamicColor,
							onChangeThemeBrand = onChangeThemeBrand,
							onChangeDynamicColorPreference = onChangeDynamicColorPreference,
							onChangeDarkThemeConfig = onChangeDarkThemeConfig,
						)
					}
				}
				HorizontalDivider(Modifier.padding(top = 8.dp))
			}
		},
		onDismissRequest = { onDismiss() },
		confirmButton = { /*TODO*/ })
}


@Composable
private fun ColumnScope.SettingsPanel(
	settings: UserEditableSettings,
	supportDynamicColor: Boolean,
	onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
	onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
	onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
) {
	SettingsDialogSectionTitle(text = stringResource(R.string.alert_dialog_theme_section))
	Column(Modifier.selectableGroup()) {
		SettingsDialogThemeChooserRow(
			text = stringResource(R.string.alert_dialog_theme_default_item),
			selected = settings.brand == ThemeBrand.DEFAULT,
			onClick = { onChangeThemeBrand(ThemeBrand.DEFAULT) },
		)
		SettingsDialogThemeChooserRow(
			text = stringResource(R.string.alert_dialog_theme_android_item),
			selected = settings.brand == ThemeBrand.ANDROID,
			onClick = { onChangeThemeBrand(ThemeBrand.ANDROID) },
		)
	}
	AnimatedVisibility(visible = settings.brand == ThemeBrand.DEFAULT && supportDynamicColor) {
		Column {
			SettingsDialogSectionTitle(text = stringResource(R.string.alert_dialog_dynamic_section))
			Column(Modifier.selectableGroup()) {
				SettingsDialogThemeChooserRow(
					text = stringResource(R.string.yes),
					selected = settings.useDynamicColor,
					onClick = { onChangeDynamicColorPreference(true) },
				)
				SettingsDialogThemeChooserRow(
					text = stringResource(R.string.no),
					selected = !settings.useDynamicColor,
					onClick = { onChangeDynamicColorPreference(false) },
				)
			}
		}
	}
	SettingsDialogSectionTitle(text = stringResource(R.string.alert_dialog_dark_mode_section))
	Column(Modifier.selectableGroup()) {
		SettingsDialogThemeChooserRow(
			text = stringResource(R.string.system_default),
			selected = settings.darkThemeConfig == DarkThemeConfig.FOLLOW_SYSTEM,
			onClick = { onChangeDarkThemeConfig(DarkThemeConfig.FOLLOW_SYSTEM) },
		)
		SettingsDialogThemeChooserRow(
			text = stringResource(R.string.light),
			selected = settings.darkThemeConfig == DarkThemeConfig.LIGHT,
			onClick = { onChangeDarkThemeConfig(DarkThemeConfig.LIGHT) },
		)
		SettingsDialogThemeChooserRow(
			text = stringResource(R.string.dark),
			selected = settings.darkThemeConfig == DarkThemeConfig.DARK,
			onClick = { onChangeDarkThemeConfig(DarkThemeConfig.DARK) },
		)
	}
}

@Composable
private fun SettingsDialogSectionTitle(text: String) {
	Text(
		text = text,
		style = MaterialTheme.typography.titleMedium,
		modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
	)
}

@Composable
fun SettingsDialogThemeChooserRow(
	text: String,
	selected: Boolean,
	onClick: () -> Unit,
) {
	Row(
		Modifier
			.fillMaxWidth()
			.selectable(
				selected = selected,
				role = Role.RadioButton,
				onClick = onClick,
			)
			.padding(12.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		RadioButton(
			selected = selected,
			onClick = null,
		)
		Spacer(Modifier.width(8.dp))
		Text(text)
	}
}


@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
//	SettingsDialog()
}