package com.duyts.core.data.repository

import com.duyts.core.common.BackgroundScope
import com.duyts.core.datastore.auth.UserAuthenticationDataSource
import com.duyts.core.datastore.model.UserAuthData
import com.duyts.core.firebase.AppFirebase
import com.duyts.core.firebase.model.AppGoogleSignInAccount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthenticateRepositoryImpl @Inject constructor(
	private val appFirebase: AppFirebase,
	private val userAuthDataStore: UserAuthenticationDataSource,
	@BackgroundScope private val backgroundScope: CoroutineScope,
) : AuthenticateRepository {

	override suspend fun authenticate(email: String, password: String): Result<Boolean> =
		backgroundScope.async {
			try {
				appFirebase.signInWithEmailAndPassword(email, password)?.let { authUser ->
						userAuthDataStore.setEmail(authUser.email)
						userAuthDataStore.setDisplayName(authUser.displayName)
						userAuthDataStore.setPhotoUrl(authUser.photoUrl?.encodedPath)
						userAuthDataStore.setAccessToken("NOT IMPLEMENTED")
						Result.success(true)
					} ?: Result.failure(Exception("No user login data!"))
			} catch (e: Exception) {
				Result.failure(e)
			}
		}.await()

	override fun getUserData(): Flow<UserAuthData> = userAuthDataStore.data
	override fun isAuthenticated(): Flow<Boolean> = userAuthDataStore.data.map {
		it.email.isNotEmpty()
	}

	override suspend fun logout() {
		userAuthDataStore.clear()
		appFirebase.logout()
	}

	override suspend fun loginWithGoogle(account: AppGoogleSignInAccount) = account.run {
		userAuthDataStore.setEmail(email)
		userAuthDataStore.setDisplayName(displayName)
		userAuthDataStore.setPhotoUrl(photoUrl?.toString())
	}
}