package com.duyts.features.auth

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.duyts.core.design.LoadingSpinner
import com.duyts.core.firebase.GoogleAuthResultContract


private const val GOOGLE_AUTH_REQUEST_CODE = 1
@Composable
fun LoginScreen(
	loginViewModel: LoginViewModel = hiltViewModel(),
	onAuthSuccess: () -> Unit = {}
) {

	val uiState = loginViewModel.loginUiState.collectAsState().value
	val context = LocalContext.current
	val startForResult =
		rememberLauncherForActivityResult(
			contract = GoogleAuthResultContract()
		) { task ->

			loginViewModel.loginWithGoogle(task)
			onAuthSuccess.invoke()
		}
	LaunchedEffect(uiState.isAuthenticated) {
		if (uiState.isAuthenticated) {
			onAuthSuccess.invoke()
		}
	}

	LaunchedEffect(uiState.errorMessage) {
		uiState.errorMessage?.let {
			Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
		}
	}

	Box(modifier = Modifier.fillMaxSize()) {
		if (uiState.isLoading) {
			LoadingSpinner()
		}

		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(24.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Column(
				modifier = Modifier
					.weight(3f)
					.padding(bottom = 12.dp),
				verticalArrangement = Arrangement.Bottom,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Text(
					text = "Welcome Back",
					style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold)
				)
				Text(
					text = "Login to your account",
					style = TextStyle(fontSize = 18.sp),
					color = Color.Gray
				)
			}
			Column(
				modifier = Modifier.weight(3f),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.Center
			) {
				OutlinedTextField(
					modifier = Modifier.fillMaxWidth(),
					placeholder = { Text(text = "Email") },
					value = uiState.email,
					onValueChange = loginViewModel::onEmailChange
				)
				Spacer(modifier = Modifier.height(20.dp))
				OutlinedTextField(
					visualTransformation = PasswordVisualTransformation(),
					modifier = Modifier.fillMaxWidth(),
					value = uiState.password,
					placeholder = { Text(text = "Password") },
					onValueChange = loginViewModel::onPasswordChange
				)
				Spacer(modifier = Modifier.height(10.dp))
				Row(
					modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
				) {
					Text(
						modifier = Modifier
							.wrapContentSize()
							.clickable { },
						text = "Forgot your password?",
						style = TextStyle(fontWeight = FontWeight.SemiBold)
					)
				}
				Spacer(modifier = Modifier.height(20.dp))
				Button(
					modifier = Modifier.widthIn(200.dp, 250.dp),
					onClick = loginViewModel::login
				) {
					Text(text = "Login")
				}
			}
			Column(
				modifier = Modifier.weight(1.5f),
				verticalArrangement = Arrangement.SpaceAround,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				ContinueWithUI()
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceEvenly
				) {

					LoginType.entries.map {
						IconButton(onClick = {
							startForResult.launch(GOOGLE_AUTH_REQUEST_CODE)
						}) {
							Icon(imageVector = Icons.Default.Person, contentDescription = null)
						}
					}
				}

				SignUpText()
			}
		}
	}
}


@Composable
fun ContinueWithUI() {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		HorizontalDivider(
			modifier = Modifier
				.weight(1f)
				.height(1.dp)
		)
		Text(
			text = "Or continue with",
			fontSize = 16.sp,
			modifier = Modifier.padding(horizontal = 8.dp)
		)
		HorizontalDivider(
			modifier = Modifier
				.weight(1f)
				.height(1.dp)
		)
	}
}

@Composable
fun SignUpText() {
	val signUpText = buildAnnotatedString {
		append("Already have an account? ")
		withStyle(
			style = SpanStyle(
				color = Color.Green, textDecoration = TextDecoration.Underline
			)
		) {
			append("Sign up")
		}
	}

	Text(text = signUpText, fontSize = 16.sp, modifier = Modifier
		.padding(16.dp)
		.clickable {
			// Handle click event, navigate to Sign Up screen
		})
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
	LoginScreen()
}