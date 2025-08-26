package com.pilafme.weekflow

import android.content.Context
import androidx.room.Room
import java.time.LocalDate

class TaskRepository(context: Context) {
    private val database = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "weekflow-db"
    ).build()

    private val dao = database.taskDao()

    fun getTasksForDate(date: LocalDate) = dao.getTasksForDate(date)

    suspend fun addTask(task: Task) = dao.insert(task)

    suspend fun updateTask(task: Task) = dao.update(task)

    suspend fun deleteTask(task: Task) = dao.delete(task)

    suspend fun hasTasksToday(date: LocalDate) = dao.hasTasksToday(date) > 0
}