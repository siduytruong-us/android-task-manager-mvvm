package com.duyts.core.firebase

import com.duyts.core.firebase.model.AppGoogleSignInAccount
import com.google.firebase.auth.AuthCredential

interface AppFirebase {
	suspend fun signInWithEmailAndPassword(email: String, password: String): AppGoogleSignInAccount?
	suspend fun signInWithToken(token:String): AppGoogleSignInAccount?
	fun logout();
}