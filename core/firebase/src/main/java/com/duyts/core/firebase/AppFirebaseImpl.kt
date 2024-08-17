package com.duyts.core.firebase

import com.duyts.core.firebase.model.AppGoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AppFirebaseImpl @Inject constructor(
	private val auth: FirebaseAuth
) : AppFirebase {

	override suspend fun signInWithEmailAndPassword(
		email: String, password: String
	): AppGoogleSignInAccount? =
		auth.signInWithEmailAndPassword(email, password).await()?.user?.let {
			AppGoogleSignInAccount(
				it.uid,
				it.displayName,
				it.email,
				it.photoUrl,
			)
		}

	override fun logout() {
		auth.signOut()
	}
}