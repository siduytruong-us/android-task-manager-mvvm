package com.duyts.core.data.repository

import com.duyts.core.datastore.model.UserAuthData
import com.duyts.core.firebase.model.AppGoogleSignInAccount
import kotlinx.coroutines.flow.Flow

interface AuthenticateRepository {
	suspend fun authenticate(email: String, password: String): Result<Boolean>
	fun getUserData(): Flow<UserAuthData>
	fun isAuthenticated(): Flow<Boolean>
	suspend fun logout()
	suspend fun loginWithGoogle(account: AppGoogleSignInAccount)
}