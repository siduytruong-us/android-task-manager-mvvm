package com.duyts.core.design

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun LoadingSpinner() {
	remember { MutableInteractionSource() }
	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(Color.Gray.copy(alpha = 0.5f))
			.pointerInput(Unit) {},
		contentAlignment = Alignment.Center
	) {
		CircularProgressIndicator()
	}
}