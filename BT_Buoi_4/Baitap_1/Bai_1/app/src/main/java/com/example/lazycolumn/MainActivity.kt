package com.example.lazycolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    Navigation(navController = navController)
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) { // Sử dụng NavHostController
    NavHost(navController = navController, startDestination = "root") {
        composable("root") {
            RootScreen(navController = navController)
        }
        composable("list") {
            ListScreen(navController = navController)
        }
        composable("detail") {
            DetailScreen(navController = navController)
        }
    }
}

@Composable
fun RootScreen(navController: NavHostController) { // Sử dụng NavHostController
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_1),
            contentDescription = "Navigation",
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Title Text
        Text(
            text = "Navigation",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 25.dp)
        )
        // Text description
        Text(
            text = "is a framework that simplifies the implementation of navigation between different UI components (activities, fragments, or composables) in an app",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Spacer(modifier = Modifier.height(200.dp))

        Button(
            onClick = { navController.navigate("list") },
            modifier = Modifier
                .width(350.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF33CCFF),
                contentColor = Color.White
            )
        ) {
            Text("PUSH", fontWeight = FontWeight.Bold,fontSize = 20.sp)
        }
    }
}

@Composable
fun ListScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Thanh tiêu đề tùy chỉnh
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                modifier = Modifier.clickable { navController.popBackStack() }
            )
            Text(
                text = "LazyColumn",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = Color(0xFF33CCFF)
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(100) { index ->
                ListItem(index = index, backgroundColor = Color(0xFF33CCFF), navController = navController) // Truyền navController
            }
        }
    }
}
@Composable
fun ListItem(index: Int, backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,navController: NavHostController) {
    val formattedIndex = String.format("%02d", index + 1) // Định dạng index thành chuỗi có 2 chữ số
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$formattedIndex | The only way to do great work is to love what you do.",
                modifier = Modifier.weight(1f),
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Detail",
                modifier = Modifier.clickable { navController.navigate("detail") } // Xử lý sự kiện click
            )
        }
    }
}

@Composable
fun DetailScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Thanh tiêu đề tùy chỉnh
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                modifier = Modifier.clickable { navController.popBackStack() }
            )
            Text(
                text = "Detail",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = Color(0xFF33CCFF)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Chiếm phần còn lại
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "\"The only way to do great work is to love what you do\"",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 60.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Box( // Thêm Box để tạo nền màu cho đoạn trích
                modifier = Modifier
                    .width(300.dp)
                    .padding(16.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFB0E0E6),
                                Color(0xFF33CCFF) // Màu nền nhạt
                            )
                        )
                    )
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "\"The only way to do great work is to love what you do.\"",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 50.sp // Thêm lineHeight để chỉnh khoảng cách dòng
                    )
                    Text(
                        text = "Steve Jobs",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(60.dp))

                    Text(
                        text = "http://quotes.thisgrandpablogs.com/",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.navigate("root") },
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF33CCFF))
            ) {
                Text(text = "BACK TO ROOT", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun NavigationButton(navController: NavHostController, route: String, text: String) { // Sử dụng NavHostController
    Button(onClick = { navController.navigate(route) }) {
        Text(text)
    }
}
@Preview(showBackground = true)
@Composable
fun RootScreenPreview() {
    MaterialTheme {
        RootScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    MaterialTheme {
        ListScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    MaterialTheme {
        DetailScreen(navController = rememberNavController())
    }
}