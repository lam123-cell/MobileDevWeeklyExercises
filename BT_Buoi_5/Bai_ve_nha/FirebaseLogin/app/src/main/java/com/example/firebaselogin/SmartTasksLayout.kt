package com.example.firebaselogin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.Row
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun SmartTasksLayout(navController: NavController) {
    val context = LocalContext.current
    val auth = remember { FirebaseAuth.getInstance() }
    val googleSignInClient = remember { getGoogleSignInClient(context) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data).continueWithTask {
                val acct = it.result
                val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
                auth.signInWithCredential(credential)
            }
            task.addOnSuccessListener {
                // Đăng nhập Firebase thành công
                val user = auth.currentUser
                Log.d("FirebaseAuth", "Đăng nhập thành công: ${user?.displayName} - ${user?.email}")
                navController.navigate("profile") // Điều hướng đến ProfileScreen
            }.addOnFailureListener { e ->
                Log.e("FirebaseAuth", "Đăng nhập thất bại: ${e.message}", e)
                // Xử lý lỗi đăng nhập
            }
        } else {
            // Xử lý trường hợp người dùng không chọn tài khoản Google
            Log.d("FirebaseAuth", "Đăng nhập Google bị hủy")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        // Ảnh ở trên
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Tiêu đề SmartTasks
        Text(
            text = "SmartTasks",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF33CCFF).copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Mô tả "A simple..."
        Text(
            text = "A simple and efficient to-do app",
            fontSize = 16.sp,
            color = Color(0xFF33CCFF).copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(60.dp))

        // Phần "Welcome"
        Text(
            text = "Welcome",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Ready to explore? Log in to get started.",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Nút "Sign in with Google"
        Button(
            onClick = {
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF33CCFF).copy(alpha = 0.4f))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "SIGN IN WITH GOOGLE",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(175.dp))

        // Dòng chữ ở cuối màn hình
        Text(
            text = "© UTHSmartTasks",
            fontSize = 14.sp,
            color = Color.Black.copy(alpha = 0.6f)
        )
    }
}

private fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(context, gso)
}

@Preview(showBackground = true)
@Composable
fun SmartTasksLayoutPreview() {
    val navController = rememberNavController()
    Surface {
        SmartTasksLayout(navController)
    }
}