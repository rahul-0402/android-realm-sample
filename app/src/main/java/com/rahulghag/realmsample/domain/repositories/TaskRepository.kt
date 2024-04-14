package com.rahulghag.realmsample.domain.repositories

import com.rahulghag.realmsample.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>

    suspend fun addTask(title: String)

    suspend fun updateTask(id: String, isCompleted: Boolean)

    suspend fun deleteCompletedTasks()
}