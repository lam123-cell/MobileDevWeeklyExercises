package com.example.database_room.viewmodel

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.*
import com.example.database_room.data.Task
import com.example.database_room.data.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository = TaskRepository.getRepository(application)
    val tasks: LiveData<List<Task>> = repository.allTasks.asLiveData()

    // LiveData để gửi thông báo lỗi về UI
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun addTask(title: String, description: String) {
        if (title.isBlank() || description.isBlank()) {
            _errorMessage.value = "Please enter both Task and Description."
        } else {
            viewModelScope.launch {
                val newTask = Task(
                    title = title,
                    description = description,
                    color =  0xFFBBDEFB.toInt()
                )
                repository.insertTask(newTask)
                _errorMessage.value = null // Reset error message nếu thêm task thành công
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}

class TaskViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
