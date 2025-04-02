package com.example.mvvmlogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.mvvmlogin.ui.screens.LoginFailureScreen
import com.example.mvvmlogin.ui.screens.LoginScreen
import com.example.mvvmlogin.ui.screens.LoginSuccessScreen
import com.example.mvvmlogin.viewmodel.LoginViewModel
import com.example.mvvmlogin.viewmodel.LoginUiState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    //Khởi tạo FirebaseAuth, GoogleSignInClient
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        loginViewModel = LoginViewModel(auth)

        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful) {
                loginViewModel.handleGoogleSignInResult(task.result)
            } else {
                loginViewModel.handleGoogleSignInResult(null)
            }
        }
        //Sử dụng setContent để thiết lập giao diện
        setContent {
            //Quan sát uiState từ LoginViewModel bằng collectAsState().
            val uiState by loginViewModel.uiState.collectAsState()

            when (uiState) {
                LoginUiState.Initial, LoginUiState.Loading -> {
                    LoginScreen(onSignInClick = {
                        val signInIntent = googleSignInClient.signInIntent
                        launcher.launch(signInIntent)
                    })
                }

                is LoginUiState.Success -> {
                    LoginSuccessScreen((uiState as LoginUiState.Success).message)
                }

                is LoginUiState.Failure -> {
                    LoginFailureScreen(
                        errorMessage = (uiState as LoginUiState.Failure).errorMessage,
                        onRetryClick = {
                            val signInIntent = googleSignInClient.signInIntent
                            launcher.launch(signInIntent)
                        }
                    )
                }
            }
        }
    }
}