package com.example.database_room.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.database_room.ui.screen.AddTaskScreen
import com.example.database_room.ui.screen.ListScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "task_list") {
        composable("task_list") {
            ListScreen(navController)
        }
        composable("add_task") {
            AddTaskScreen(navController)
        }
    }
}