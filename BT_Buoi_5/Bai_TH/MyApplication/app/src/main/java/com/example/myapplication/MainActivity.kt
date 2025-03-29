package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.background
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle


class MainActivity : ComponentActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful) {
                val account = task.result
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userEmail = auth.currentUser?.email
                        message.value = "Success!\n Hi  $userEmail\nWelcome to UTHSmartTasks!"
                    } else {
                        message.value = "Google Sign-In Failed: ${it.exception?.message}"
                    }
                }
            } else {
                message.value = "Google Sign-In Failed\n User canceled the Google sign-in process"
            }
        }

        setContent {
            val msg by message

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            val signInIntent = googleSignInClient.signInIntent
                            launcher.launch(signInIntent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4285F4),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .width(300.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Login by Gmail",
                            style = TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    // Sử dụng Row để tạo khung màu cho thông báo
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .background(
                                color = when {
                                    msg.startsWith("Success") -> Color(0xFFE6FFFA)
                                    else -> Color(0xFFFCE4EC)
                                },
                                shape = RoundedCornerShape(8.dp) // Giữ nguyên bo góc cho khung thông báo
                            )
                            .padding(12.dp)
                            .fillMaxWidth(0.8f),

                    ) {
                        Text(
                            text = buildAnnotatedString {
                                val isSuccess = msg.startsWith("Success")
                                val mainColor = if (isSuccess) Color(0xFF006400) else Color(0xFFB00020)

                                if (isSuccess) {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = mainColor)) {
                                        append("Success!\n Hi")
                                    }
                                    append(msg.substringAfter("Success!\n Hi ").substringBefore("\nWelcome to "))
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = mainColor)) {
                                        append("\nWelcome to UTHSmartTasks!")
                                    }
                                } else {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = mainColor)) {
                                        append(msg.substringBefore(":"))
                                    }
                                }
                            },
                            color = when {
                                msg.startsWith("Success") -> Color(0xFF006400)
                                else -> Color(0xFFB00020)
                            },
                            style = TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                lineHeight = 24.sp // Add line spacing (line height)
                            )
                        )
                    }

                }
            }
        }
    }

    companion object {
        var message = mutableStateOf("")
    }
}
