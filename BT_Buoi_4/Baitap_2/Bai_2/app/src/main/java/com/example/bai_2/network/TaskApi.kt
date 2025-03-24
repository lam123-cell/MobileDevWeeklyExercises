package com.example.bai_2.network

import com.example.bai_2.model.TaskResponse
import retrofit2.http.GET

interface TaskApi {
    @GET("researchUTH/tasks")
    suspend fun getTasks(): TaskResponse
}