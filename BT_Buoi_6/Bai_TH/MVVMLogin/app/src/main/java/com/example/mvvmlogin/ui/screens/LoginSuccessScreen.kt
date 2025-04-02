package com.example.mvvmlogin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginSuccessScreen(message: String) {
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
                onClick = { /* Không xử lý sự kiện nhấp chuột */ },
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
                        fontSize = 18.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .background(
                        color = Color(0xFFE6FFFA),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
                    .fillMaxWidth(0.8f),

                ) {
                Text(
                    text = buildAnnotatedString {
                        val mainColor = Color(0xFF006400)
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = mainColor)) {
                            append("Success!\n Hi")
                        }
                        append(message.substringAfter("Success!\n Hi ").substringBefore("\nWelcome to "))
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = mainColor)) {
                            append("\nWelcome to UTHSmartTasks!")
                        }
                    },
                    color = Color(0xFF006400),
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp
                    )
                )
            }
        }
    }
}