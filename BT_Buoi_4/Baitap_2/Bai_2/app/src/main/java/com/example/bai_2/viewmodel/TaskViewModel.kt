package com.example.bai_2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bai_2.model.Task
import com.example.bai_2.network.RetrofitInstance
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getTasks()
                println("API Response: $response") // Log phản hồi
                _tasks.value = response.data // Lấy danh sách Task từ response.data
                _isLoading.value = false
                _errorMessage.value = null
            } catch (e: Exception) {
                println("API Error: ${e.message}") // Log lỗi
                _isLoading.value = false
                _errorMessage.value = e.message
            }
        }
    }
}