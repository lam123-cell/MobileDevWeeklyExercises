package com.example.database_room.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun insertTask(task: Task) {
        taskDao.insert(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.delete(task)
    }

    companion object {
        @Volatile
        private var INSTANCE: TaskRepository? = null

        fun getRepository(context: Context): TaskRepository {
            return INSTANCE ?: synchronized(this) {
                val database = AppDatabase.getDatabase(context)
                val instance = TaskRepository(database.taskDao())
                INSTANCE = instance
                instance
            }
        }
    }
}