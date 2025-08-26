package com.pilafme.weekflow

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val date: LocalDate,
    val completed: Boolean
)