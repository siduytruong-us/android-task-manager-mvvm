package com.duyts.core.firebase

import com.duyts.core.firebase.model.AppGoogleSignInAccount

interface AppFirebase {
	suspend fun signInWithEmailAndPassword(email: String, password: String): AppGoogleSignInAccount?
	fun logout();
}