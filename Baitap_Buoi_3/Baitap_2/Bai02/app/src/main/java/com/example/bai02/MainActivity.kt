package com.example.bai02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import androidx.compose.foundation.background


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Bai02Theme {
                NavigationFlow()
            }
        }
    }
}

@Composable
fun NavigationFlow() {
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(3000)
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
    } else {
        OnboardingScreen()
    }
}

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_1),
            contentDescription = "Splash Image",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "UTH SmartTasks",
            style = TextStyle(
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        )
    }
}

data class OnboardingPage(val title: String, val description: String, val imageRes: Int)

@Composable
fun OnboardingScreen() {
    var pageIndex by remember { mutableStateOf(0) }
    // Biến để theo dõi thời gian
    var autoNext by remember { mutableStateOf(true) }

    val pages = listOf(
        OnboardingPage(
            title = "Easy Time Management",
            description = "With management based on priority and daily tasks, it will give you " +
                    "convenience in managing and determining the tasks that must be done first",
            imageRes = R.drawable.image_3
        ),
        OnboardingPage(
            title = "Increase Work Effectiveness",
            description = "Time management and the determination of more important tasks will give your job " +
                    "statistics better and always improve",
            imageRes = R.drawable.image_4
        ),
        OnboardingPage(
            title = "Reminder Notification",
            description = "The advantage of this application is that it also provides reminders for" +
                    "you so you don't forget to keep doing your assignments well and according to thee time you have set",
            imageRes = R.drawable.image_5
        )
    )
    LaunchedEffect(pageIndex) {
        if (autoNext) {
            delay(4000) // Đợi 4 giây
            if (pageIndex < pages.size - 1) {
                pageIndex++
            } else {
                println("Get Started") // Hoặc chuyển sang màn hình chính
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // **Ba dấu chấm tròn chỉ báo trang**
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (i in pages.indices) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(if (i == pageIndex) Color.Blue else Color.LightGray, shape = androidx.compose.foundation.shape.CircleShape)
                    )
                }
            }

            // **Nút Skip**
            Text(
                text = "Skip",
                color = Color.Blue,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        if (pageIndex < pages.size -1) {
                            pageIndex++
                            autoNext = false
                        }
                    }
            )
        }
        Image(
            painter = painterResource(id = pages[pageIndex].imageRes),
            contentDescription = null,
            modifier = Modifier.size(350.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = pages[pageIndex].title,
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = pages[pageIndex].description,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        )
        Spacer(modifier = Modifier.height(100.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (pageIndex == 0) Arrangement.End else Arrangement.SpaceBetween
        ) {

            if (pageIndex > 0) {
                /*Button(onClick = { pageIndex-- }) {
                    Text("Back")
                }*/
                Image(
                    painter = painterResource(id = R.drawable.image_2),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(48.dp)
                        .clickable {
                            pageIndex--
                            autoNext = false
                        }
                )
            }
            Button(onClick = {
                if (pageIndex < pages.size - 1) pageIndex++
                else println("Get Started")
                autoNext = false
            },
                modifier = Modifier
                    .width(400.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                Text(if (pageIndex == pages.size - 1) "Get Started" else "Next")
            }
        }
    }
}

@Composable
fun Bai02Theme(content: @Composable () -> Unit) {
    // Định nghĩa theme ở đây
    // Ví dụ: sử dụng MaterialTheme
    androidx.compose.material3.MaterialTheme {
        content()
    }
}


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Bai02Theme {
        SplashScreen()
    }
}
@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    Bai02Theme {
        OnboardingScreen()
    }
}

