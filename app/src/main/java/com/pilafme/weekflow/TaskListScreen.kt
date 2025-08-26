package com.pilafme.weekflow

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TaskListScreen(date: LocalDate, viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.getTasksForDate(date).collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()
    var newTaskText by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Задачи на ${date.format(DateTimeFormatter.ofPattern("dd MMM"))}",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = newTaskText,
                onValueChange = { newTaskText = it },
                label = { Text("Новая задача") },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                if (newTaskText.isNotBlank()) {
                    coroutineScope.launch {
                        viewModel.addTask(Task(text = newTaskText, date = date, completed = false))
                        newTaskText = ""
                    }
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = "Добавить задачу")
            }
        }

        LazyColumn {
            items(tasks) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Checkbox(
                            checked = task.completed,
                            onCheckedChange = { completed ->
                                coroutineScope.launch {
                                    viewModel.updateTask(task.copy(completed = completed))
                                }
                            }
                        )
                        Text(
                            text = task.text,
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        IconButton(onClick = {
                            showDialog = true
                            selectedTask = task
                        }) {
                            Text("→", style = MaterialTheme.typography.bodyLarge)
                        }
                        IconButton(onClick = {
                            coroutineScope.launch {
                                viewModel.deleteTask(task)
                            }
                        }) {
                            Text("🗑", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }
    }

    if (showDialog && selectedTask != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Перенести задачу") },
            text = {
                Column {
                    (1..6).forEach { offset ->
                        val newDate = date.plusDays(offset.toLong())
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.updateTask(selectedTask!!.copy(date = newDate))
                                    showDialog = false
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("На ${newDate.format(DateTimeFormatter.ofPattern("dd MMM"))}")
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}