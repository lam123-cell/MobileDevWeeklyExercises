package com.example.homework.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.homework.data.Task
import androidx.compose.ui.graphics.Color

class TaskViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        _tasks.value = listOf(
            Task(1, "Hoàn thành dự án Android", "Hoàn thiện UI, tích hợp API và viết tài liệu", Color(0xFFBBDEFB)),
            Task(2, "Hoàn thành dự án Android", "Hoàn thiện UI, tích hợp API và viết tài liệu", Color(0xFFF8BBD0)),
            Task(3, "Hoàn thành dự án Android", "Hoàn thiện UI, tích hợp API và viết tài liệu", Color(0xFFDCEDC8)),
            Task(4, "Hoàn thành dự án Android", "Hoàn thiện UI, tích hợp API và viết tài liệu", Color(0xFFFFF9C4))
        )
    }

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            val newTask = Task(
                id = (_tasks.value.maxOfOrNull { it.id } ?: 0) + 1,
                title = title,
                description = description,
                color = Color(0xFFBBDEFB)
            )
            _tasks.value = _tasks.value + newTask

        }
    }
}
