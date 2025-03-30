package com.example.firebaselogin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import coil.compose.rememberAsyncImagePainter // Thêm thư viện Coil để tải ảnh từ URL
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileScreen(navController: NavController) {
    val auth = remember { FirebaseAuth.getInstance() }
    val user = auth.currentUser
    val db = remember { FirebaseFirestore.getInstance() }
    var dateOfBirth by remember { mutableStateOf("") }

    val name = user?.displayName ?: "Không có tên"
    val email = user?.email ?: "Không có email"
    val photoUrl = user?.photoUrl?.toString() ?: "" // Lấy URL ảnh đại diện
    LaunchedEffect(user?.uid) {
        user?.uid?.let { uid ->
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        dateOfBirth = document.getString("dateOfBirth") ?: ""
                    }
                }
                .addOnFailureListener { exception ->
                    // Xử lý lỗi
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Thanh tiêu đề
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(110.dp))
            Text(
                text = "Profile",
                fontSize = 20.sp,
                color = Color(0xFF33CCFF),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Ảnh đại diện
        Box(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            if (photoUrl.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(model = photoUrl),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Image(
                painter = painterResource(id = R.drawable.mayanh),
                contentDescription = "Change Profile Picture",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.BottomEnd)
                    .clickable { /* Xử lý sự kiện thay đổi ảnh */ }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Tên
        OutlinedTextField(
            value = name,
            onValueChange = { /* Không cho phép thay đổi */ },
            label = { Text(
                "Name",
                fontWeight = FontWeight.Bold) },
            modifier = Modifier.fillMaxWidth(),
            enabled = false // Disable chỉnh sửa
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { /* Không cho phép thay đổi */ },
            label = { Text(
                "Email",
                fontWeight = FontWeight.Bold) },
            modifier = Modifier.fillMaxWidth(),
            enabled = false // Disable chỉnh sửa
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Ngày sinh
        OutlinedTextField(
            value = dateOfBirth,
            onValueChange = { /* Không cho phép thay đổi */ },
            label = { Text("Date of Birth") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false
        )

        Spacer(modifier = Modifier.weight(1f))

        // Nút Back
        Button(
            onClick = { navController.popBackStack() }, // Sử dụng popBackStack
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF33CCFF), // Màu nền
                contentColor = Color.White // Màu văn bản
            )
        ) {
            Text(
                text = "Back",
                fontSize = 14.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController)
}