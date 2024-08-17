package com.duyts.core.firebase.model

import android.net.Uri

data class AppGoogleSignInAccount(
	val id: String?,
	val displayName: String?,
	val email: String?,
	val photoUrl: Uri?,
	val expired: Boolean = false,
)