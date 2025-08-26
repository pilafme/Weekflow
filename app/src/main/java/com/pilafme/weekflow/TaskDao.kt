package com.pilafme.weekflow

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE date = :date")
    fun getTasksForDate(date: LocalDate): Flow<List<Task>>

    @Query("SELECT COUNT(*) FROM tasks WHERE date = :date AND completed = 0")
    suspend fun hasTasksToday(date: LocalDate): Int

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}