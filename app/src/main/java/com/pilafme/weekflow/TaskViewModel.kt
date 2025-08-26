package com.pilafme.weekflow

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TaskRepository(application)

    fun getTasksForDate(date: LocalDate): Flow<List<Task>> = repository.getTasksForDate(date)

    suspend fun addTask(task: Task) = repository.addTask(task)

    suspend fun updateTask(task: Task) = repository.updateTask(task)

    suspend fun deleteTask(task: Task) = repository.deleteTask(task)
}