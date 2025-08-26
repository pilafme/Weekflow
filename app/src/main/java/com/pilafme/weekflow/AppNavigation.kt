package com.pilafme.weekflow

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.toKotlinDuration
import java.time.Duration as JavaDuration

@Composable
fun AppNavigation() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val days = listOf("Сегодня", "Завтра", "+2", "+3", "+4", "+5", "+6")
    val dates = (0..6).map { LocalDate.now().plusDays(it.toLong()) }

    // Инициализация WorkManager для ежедневных уведомлений
    LaunchedEffect(Unit) {
        val workManager = WorkManager.getInstance()
        val dailyWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(calculateDelayToMorning(), TimeUnit.MILLISECONDS)
            .build()
        workManager.enqueueUniquePeriodicWork(
            "daily_notification",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyWorkRequest
        )
    }

    TabRow(selectedTabIndex = selectedTab, contentColor = Color(0xFF007AFF)) {
        days.forEachIndexed { index, day ->
            Tab(
                selected = selectedTab == index,
                onClick = { selectedTab = index },
                text = { Text(day) }
            )
        }
    }

    TaskListScreen(date = dates[selectedTab])
}

// Функция для расчета задержки до 8:00 утра
private fun calculateDelayToMorning(): Long {
    val now = LocalTime.now()
    val morning = LocalTime.of(8, 0)
    val delay = if (now.isBefore(morning)) {
        JavaDuration.between(now, morning).toKotlinDuration().inWholeMilliseconds
    } else {
        JavaDuration.between(now, morning.plusHours(24)).toKotlinDuration().inWholeMilliseconds
    }
    return delay
}