package com.example.datastore.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.datastore.DataStoreManager
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.FontWeight


@Composable
fun SettingScreen(dataStoreManager: DataStoreManager) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    var selectedColor by remember { mutableStateOf("blue") }

    val colorState = produceState<String?>(
        initialValue = null,
        producer = {
            dataStoreManager.selectedColor.collect {
                value = it
            }
        }
    )

    val backgroundColor = when (colorState.value ?: selectedColor) {
        "blue" -> Color(0xFF81D4FA)
        "pink" -> Color(0xFFD0BCFF)
        "black" -> Color(0xFF212121)
        else -> Color.White
    }

    Scaffold( // <- Gói toàn bộ trong Scaffold để hiện Snackbar
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = backgroundColor
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Setting", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Text("Choosing the right theme sets the tone...", fontSize = 14.sp, color = Color.White)

                Spacer(modifier = Modifier.height(20.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    ThemeColorBox(Color(0xFF81D4FA), selectedColor == "blue") {
                        selectedColor = "blue"
                    }
                    ThemeColorBox(Color(0xFFD0BCFF), selectedColor == "pink") {
                        selectedColor = "pink"
                    }
                    ThemeColorBox(Color(0xFF212121), selectedColor == "black") {
                        selectedColor = "black"
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(onClick = {
                    scope.launch {
                        dataStoreManager.saveColor(selectedColor)
                        snackbarHostState.showSnackbar("Saved successfully 🎉")
                    }
                }) {
                    Text("Apply")
                }
            }
        }
    }
}


@Composable
fun ThemeColorBox(color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color)
            .border(
                width = if (isSelected) 3.dp else 0.dp,
                color = if (isSelected) Color.White else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
    )
}
