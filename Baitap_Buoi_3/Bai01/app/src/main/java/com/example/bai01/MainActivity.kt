package com.example.bai01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("ui_list") { UIComponentsList(navController) }
        composable("text_detail") { TextDetailScreen(navController) }
        composable("image_detail") { ImageDetailScreen(navController) }
        composable("textfield_detail") { TextFieldDetailScreen(navController) }
        composable("passwordfield_detail") { PasswordFieldDetailScreen(navController) }
        composable("column_detail") { ColumnDetailScreen(navController) }
        composable("row_detail") { RowDetailScreen(navController) }

    }
}

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_1),
            contentDescription = "Image 1",
            modifier = Modifier.size(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.image_2),
            contentDescription = "Image 2",
            modifier = Modifier.size(300.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("ui_list") },
            modifier = Modifier
                .padding(16.dp)
                .size(width = 400.dp, height = 60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            ),
        ) {
            Text("I'm ready")
        }
    }

}

@Composable
fun UIComponentsList(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
        Text(
            "UI Components List",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0080FF) // Màu xanh nước biển
        )}
        Spacer(modifier = Modifier.height(16.dp))

        // Display Section
        Text("Display", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
        ComponentItem("Text", "Displays text", navController)
        ComponentItem("Image", "Displays an image", navController)

        Spacer(modifier = Modifier.height(16.dp))

        // Input Section
        Text("Input", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
        ComponentItem("TextField", "Input field for text", navController)
        ComponentItem("PasswordField", "Input field for passwords", navController)

        Spacer(modifier = Modifier.height(16.dp))

        // Layout Section
        Text("Layout", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
        ComponentItem("Column", "Arranges elements vertically", navController)
        ComponentItem("Row", "Arranges elements horizontally", navController)
    }
}

@Composable
fun TextDetailScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Nút back
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
                modifier = Modifier
                    .clickable { navController.popBackStack() } // Quay lại màn hình trước
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.width(100.dp))

            Text(

                "Text Detail",

                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0080FF)

            )
        }


        // Văn bản chính
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                buildAnnotatedString {
                    append("The")
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                        append("quick ")
                    }
                    withStyle(style = SpanStyle(color = Color(0xFFD9822B))) {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("B")
                        }
                        append("rown")
                    }
                    append(" fox ")
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic, letterSpacing = 4.sp)) {
                        append("jumps")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("over")
                    }
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append("the")
                    }
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                        append("lazy")
                    }
                    append("dog.")
                },
                style = TextStyle(fontSize = 30.sp)
            )
        }
    }
}
@Composable
fun ImageDetailScreen(navController: NavController) {
    ScreenDetailTemplate(navController, "Image Detail") {
        Image(
            painter = painterResource(id = R.drawable.image_4),
            contentDescription = "Image 4",
            modifier = Modifier.size(400.dp)
        )
    }
}

@Composable
fun TextFieldDetailScreen(navController: NavController) {
    var text by remember { mutableStateOf("") }
    ScreenDetailTemplate(navController, "TextField Detail") {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Họ và tên") }
        )
    }
}

@Composable
fun PasswordFieldDetailScreen(navController: NavController) {
    var password by remember { mutableStateOf("") }
    ScreenDetailTemplate(navController, "PasswordField Detail") {
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            visualTransformation = PasswordVisualTransformation()
        )
    }
}

@Composable
fun ColumnDetailScreen(navController: NavController) {
    ScreenDetailTemplate(navController, "Column Layout Detail") {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.size(100.dp).background(Color.LightGray))
            Spacer(Modifier.height(20.dp))
            Box(Modifier.size(100.dp).background(Color.Gray))
            Spacer(Modifier.height(20.dp))
            Box(Modifier.size(100.dp).background(Color.DarkGray))
        }
    }
}

@Composable
fun RowDetailScreen(navController: NavController) {
    ScreenDetailTemplate(navController, "Row Layout Detail") {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Box(Modifier.size(80.dp).background(Color.LightGray))
            Spacer(Modifier.width(10.dp))
            Box(Modifier.size(80.dp).background(Color.Gray))
            Spacer(Modifier.width(10.dp))
            Box(Modifier.size(80.dp).background(Color.DarkGray))
        }
    }
}


@Composable
fun ScreenDetailTemplate(navController: NavController, title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Nút back và tiêu đề
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
                modifier = Modifier
                    .clickable { navController.popBackStack() } // Quay lại màn hình trước
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.width(100.dp))
            Text(
                title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0080FF)
            )
        }

        // Nội dung chi tiết
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}
@Composable
fun ComponentItem(title: String, description: String, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("${title.lowercase()}_detail") }
            .background(Color(0xFFD0E8FF), RoundedCornerShape(8.dp)) // Thêm nền và bo góc
            .padding(16.dp), // Thêm padding bên trong
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp) 
            Text(description, fontSize = 14.sp, color = Color.Black)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    val navController = rememberNavController()
    WelcomeScreen(navController)
}
@Preview(showBackground = true)
@Composable
fun UIComponentsListPreview() {
    val navController = rememberNavController()
    UIComponentsList(navController)
}
@Preview(showBackground = true)
@Composable
fun TextDetailScreenPreview() {
    val navController = rememberNavController()
    TextDetailScreen(navController)
}

@Preview(showBackground = true)
@Composable
fun ImageDetailScreenPreview() {
    val navController = rememberNavController()
    ImageDetailScreen(navController)
}
@Preview(showBackground = true)
@Composable
fun TextFieldDetailScreenPreview() {
    val navController = rememberNavController()
    TextFieldDetailScreen(navController)
}
@Preview(showBackground = true)
@Composable
fun PasswordFieldDetailScreenPreview() {
    val navController = rememberNavController()
    PasswordFieldDetailScreen(navController)
}
@Preview(showBackground = true)
@Composable
fun ColumnDetailScreenPreview() {
    val navController = rememberNavController()
    ColumnDetailScreen(navController)
}
@Preview(showBackground = true)
@Composable
fun RowDetailScreenPreview() {
    val navController = rememberNavController()
    RowDetailScreen(navController)
}