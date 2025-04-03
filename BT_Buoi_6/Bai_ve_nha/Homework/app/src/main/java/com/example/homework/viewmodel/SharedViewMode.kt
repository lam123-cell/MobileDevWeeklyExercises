package com.example.homework.viewmodel

import androidx.lifecycle.ViewModel
import com.example.homework.ui.screen.TaskItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.util.Log

class SharedViewModel : ViewModel() {
    private val _taskList = MutableStateFlow<List<TaskItem>>(emptyList())
    val taskList: StateFlow<List<TaskItem>> = _taskList

    fun addTask(task: TaskItem) {
        _taskList.value = _taskList.value + task
        Log.d("SharedViewModel", "Task added: $task")
    }
}
