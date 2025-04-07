package com.example.homework.data

import androidx.compose.ui.graphics.Color

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val color: Color
)