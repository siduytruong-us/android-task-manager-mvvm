package com.duyts.core.firebase

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.duyts.core.firebase.model.AppGoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class GoogleAuthResultContract : ActivityResultContract<Int, AppGoogleSignInAccount?>() {
	override fun createIntent(context: Context, input: Int): Intent =
		getGoogleSignInClient(context).signInIntent.putExtra("input", input)

	override fun parseResult(resultCode: Int, intent: Intent?): AppGoogleSignInAccount? =
		when (resultCode) {
			Activity.RESULT_OK -> GoogleSignIn.getSignedInAccountFromIntent(intent)
				.getResult(ApiException::class.java)?.let {
					AppGoogleSignInAccount(
						id = it.id,
						displayName = it.displayName,
						email = it.email,
						photoUrl = it.photoUrl,
						expired = it.isExpired,
					)
				}

			else -> null
		}

}

fun getGoogleSignInClient(context: Context): GoogleSignInClient {
	val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//         Request id token if you intend to verify google user from your backend server
//        .requestIdToken(context.getString(R.string.backend_client_id))
		.requestEmail()
		.build()

	return GoogleSignIn.getClient(context, signInOptions)
}