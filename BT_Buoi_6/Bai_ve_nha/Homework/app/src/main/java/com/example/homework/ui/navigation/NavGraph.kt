package com.example.homework.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.homework.ui.screen.AddTaskScreen
import com.example.homework.ui.screen.ListScreen

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