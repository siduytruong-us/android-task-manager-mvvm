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

class GoogleAuthResultContract : ActivityResultContract<Int, String?>() {
	override fun createIntent(context: Context, input: Int): Intent =
		getGoogleSignInClient(context).signInIntent.putExtra("input", input)

	override fun parseResult(resultCode: Int, intent: Intent?): String? =
		when (resultCode) {
			Activity.RESULT_OK ->
				GoogleSignIn.getSignedInAccountFromIntent(intent)
					.getResult(ApiException::class.java)?.idToken

			else -> null
		}

}

fun getGoogleSignInClient(context: Context): GoogleSignInClient {
	val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
		.requestIdToken(context.getString(R.string.default_web_client_id))
		.requestProfile()
		.requestEmail()
		.build()

	return GoogleSignIn.getClient(context, signInOptions)
}