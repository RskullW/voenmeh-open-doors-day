package ru.voenmeh.openday.domain.repository

import ru.voenmeh.openday.domain.model.Quest

interface AchievementRepository {
    suspend fun fromDevice(): List<String>
    suspend fun saveToDevice(quests: List<String>)
}