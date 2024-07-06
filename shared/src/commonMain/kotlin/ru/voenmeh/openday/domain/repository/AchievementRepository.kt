package ru.voenmeh.openday.domain.repository

import ru.voenmeh.openday.domain.model.Achievement

interface AchievementRepository {
    suspend fun fromDevice(): List<Achievement>
    suspend fun saveToDevice(achievements: List<Achievement>)
}