package com.example.bai_2

import com.example.bai_2.model.Task
import androidx.compose.foundation.background
import android.os.Bundle
import androidx.compose.ui.text.style.TextAlign
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import com.example.bai_2.ui.theme.Bai_2Theme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bai_2.viewmodel.TaskViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.clickable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bai_2.model.Subtask
import com.example.bai_2.model.Attachment
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.Icon
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.compose.NavHost



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            Bai_2Theme {

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "task_list") {

                    composable("task_list") {

                        ListScreen(navController = navController)

                    }

                    composable("task_detail/{taskId}") { backStackEntry ->
                        val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
                        val viewModel: TaskViewModel = viewModel()
                        val tasks by viewModel.tasks.observeAsState(initial = emptyList())
                        val task = tasks.find { it.id == taskId }

                        if (task != null) {
                            TaskDetailScreen(task = task,navController = navController)
                        } else {
                            // Xử lý trường hợp task == null
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                            ) {
                                Text("Task not found")
                            }

                        }

                    }

                }

            }

        }

    }
    }

@Composable
fun ListScreen(navController: NavController) {
    val viewModel: TaskViewModel = viewModel()
    val tasks = viewModel.tasks.observeAsState(initial = emptyList()).value
    val isLoading = viewModel.isLoading.observeAsState(initial = false).value
    val errorMessage = viewModel.errorMessage.observeAsState(initial = null).value

    println("Tasks: $tasks") // Thêm dòng này để log tasks

    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Thanh tiêu đề (Logo và Tiêu đề)
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo1),
                        contentDescription = "UTH Logo",
                        modifier = Modifier
                            .size(50.dp)

                    )

                    Column(
                        modifier = Modifier.padding(start = 12.dp)
                    ) {
                        Text(
                            text = "SmartTasks",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color(0xFF33CCFF),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "A simple and efficient to-do app",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF33CCFF)
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.bell),
                        contentDescription = "Bell",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(start = 40.dp)
                    )
                }
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (errorMessage != null) {
                Text(text = "Error: $errorMessage")
            } else if (tasks.isEmpty()) {
                EmptyTaskListScreen()
            } else {
                LazyColumn {
                    items(tasks) { task ->
                        TaskItem(task = task, navController = navController)// Truyền NavController
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task,navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("task_detail/${task.id}") // Điều hướng đến TaskDetailScreen
    },
        colors = CardDefaults.cardColors(
            containerColor = if (task.isCompleted) Color(0xFFE0F2E0) else Color(0xFFE1F5FE)
        )
    ){
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Sắp xếp các thành phần cách đều nhau
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { /* Xử lý sự kiện khi checkbox được thay đổi */ }
            )

            Column(
                modifier = Modifier.weight(1f) // Chiếm toàn bộ không gian còn lại
            ) {
                Text(text = task.title, fontWeight = FontWeight.Bold)
                Text(text = task.description)
                Text(text = "Status: ${task.status}",fontWeight = FontWeight.Bold)
                Text(text = "${task.dueDate}") // Hiển thị dueDate
            }
        }
    }
}

@Composable
fun EmptyTaskListScreen() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val columnWidth = screenWidth - 52.dp // 16dp padding mỗi bên

    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Column 1: Logo và Tiêu đề
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "UTH Logo",
                        modifier = Modifier.size(50.dp)
                    )

                    Column(
                        modifier = Modifier.padding(start = 12.dp)
                    ) {
                        Text(
                            text = "SmartTasks",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color(0xFF33CCFF),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "A simple and efficient to-do app",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF33CCFF)
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.bell),
                        contentDescription = "Bell",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(start = 40.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Column 2: Thông báo "No Tasks Yet!"
            Column(
                modifier = Modifier
                    .width(columnWidth)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.clipboard_sleep),
                    contentDescription = "No Tasks Icon",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "No Tasks Yet!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Stay productive - add something to do",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun TaskDetailScreen(task: Task,navController: NavController) {

    Scaffold(

        topBar = {
            CustomAppBar(navController = navController)
        },
        modifier = Modifier.padding(top = 50.dp)

    ) { paddingValues ->

        Column(

            modifier = Modifier

                .fillMaxSize()

                .padding(paddingValues)

                .padding(16.dp)

        ) {

// Tiêu đề Task

            Text(

                text = task.title,

                style = MaterialTheme.typography.headlineMedium,

                fontWeight = FontWeight.Bold

            )



// Mô tả Task

            Text(

                text = task.description,

                style = MaterialTheme.typography.bodyLarge,

                modifier = Modifier.padding(top = 8.dp)

            )



// Thông tin Task (Category, Status, Priority)

            Row(

                modifier = Modifier

                    .fillMaxWidth()

                    .padding(top = 16.dp),

                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                InfoBox(title = "Category", value = task.category)

                InfoBox(title = "Status", value = task.status)

                InfoBox(title = "Priority", value = task.priority)

            }



// Subtasks

            Text(

                text = "Subtasks",

                style = MaterialTheme.typography.titleMedium,

                modifier = Modifier.padding(top = 16.dp)

            )

            task.subtasks.forEach { subtask ->

                SubtaskItem(subtask = subtask)

            }



// Attachments
            Text(
                text = "Attachments",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
            task.attachments.forEach { attachment ->
                AttachmentItem(attachment = attachment)
            }
        }
    }
}

@Composable
fun CustomAppBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = "Back",
            modifier = Modifier
                .padding(end = 8.dp)
                .clickable {
                    navController.popBackStack() // Điều hướng về màn hình trước đó
                }

        )
        Text(
            text = "Detail",
            style = TextStyle(
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF33CCFF)
            ),
            modifier = Modifier.padding(start = 100.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.delete),
            contentDescription = "Delete",
            modifier = Modifier.padding(start = 110.dp)

        )
    }
}

@Composable
fun InfoBox(title: String, value: String) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .background(Color.LightGray)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, fontWeight = FontWeight.Bold)
        Text(text = value, fontSize = 14.sp)
    }
}

@Composable
fun SubtaskItem(subtask: Subtask) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = subtask.isCompleted,
            onCheckedChange = { /* Xử lý sự kiện khi checkbox được thay đổi */ }
        )
        Text(text = subtask.title)
    }
}

@Composable
fun AttachmentItem(attachment: Attachment) {
    val context = LocalContext.current

    Row(
        modifier = Modifier.padding(8.dp).clickable {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(attachment.fileUrl))
            context.startActivity(intent)
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.tep),
            contentDescription = "Attachment"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = attachment.fileName ?: "Attachment",
            color = Color.Blue,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Composable
fun BottomNavigationBar() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        NavigationItem("Home", Icons.Filled.Home),
        NavigationItem("Calendar", Icons.Filled.DateRange),
        NavigationItem("Add", Icons.Filled.Add),
        NavigationItem("List", Icons.Filled.List),
        NavigationItem("Settings", Icons.Filled.Settings)
    )

    BottomAppBar(
        modifier = Modifier
            .height(100.dp) // Điều chỉnh chiều cao nếu cần
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}

data class NavigationItem(val title: String, val icon: ImageVector)

@Preview(showBackground = true)
@Composable
fun EmptyTaskListScreenPreview() {
    Bai_2Theme {
        EmptyTaskListScreen()
    }
}
