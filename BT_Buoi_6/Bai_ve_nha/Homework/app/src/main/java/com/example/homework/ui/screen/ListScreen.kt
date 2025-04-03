package com.example.homework.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.ui.text.font.FontWeight
import com.example.homework.R
import androidx.navigation.NavController
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homework.viewmodel.SharedViewModel
import android.util.Log

data class TaskItem(val task: String, val description: String)

@Composable
fun ListScreen(navController: NavController,sharedViewModel: SharedViewModel = viewModel()) {
    var selectedItem by remember { mutableStateOf(0) }
    val tasks by sharedViewModel.taskList.collectAsState()
    Log.d("ListScreen", "Tasks: $tasks")

    Scaffold(
        bottomBar = {
            BottomAppBar {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedItem == 0,
                    onClick = { selectedItem = 0 }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.DateRange, contentDescription = "Calendar") },
                    label = { Text("Calendar") },
                    selected = selectedItem == 1,
                    onClick = { selectedItem = 1 }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Add, contentDescription = "Add") },
                    label = { Text("Add") },
                    selected = selectedItem == 2,
                    onClick = { navController.navigate("addTaskScreen") } // Điều hướng đến AddTaskScreen
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.List, contentDescription = "List") },
                    label = { Text("List") },
                    selected = selectedItem == 3,
                    onClick = { selectedItem = 3 }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = selectedItem == 4,
                    onClick = { selectedItem = 4 }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Xử lý sự kiện back */ }) {
                    Image(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back",
                        modifier = Modifier.size(26.dp)
                    )
                }
                Text("List",
                    style = MaterialTheme.typography.h6,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF33CCFF)
                )
                IconButton(onClick = { navController.navigate("addTaskScreen") }) { // Điều hướng đến AddTaskScreen
                    Image(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = "Add",
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                items(tasks) { taskItem -> // Hiển thị danh sách tasks
                    ListItem(
                        text = { Text(taskItem.task) },
                        secondaryText = { Text(taskItem.description) },
                        modifier = Modifier.padding(8.dp),
                        backgroundColor = Color.LightGray.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun ListItem(
    text: @Composable () -> Unit,
    secondaryText: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent
) {
    Surface(
        color = backgroundColor,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            text()
            secondaryText()
        }
    }
}
