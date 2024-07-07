package ru.voenmeh.openday.data.repository

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.voenmeh.openday.data.infrastructure.PreferenceStorage
import ru.voenmeh.openday.domain.model.Achievement
import ru.voenmeh.openday.domain.model.Quest
import ru.voenmeh.openday.domain.repository.AchievementRepository

class AchievementRepositoryImpl(private val preferenceStorage: PreferenceStorage): AchievementRepository {
    override suspend fun fromDevice(): List<String> {
        val jsonString = preferenceStorage.getString(PREFS_NAME)

        val achievementsUID: List<String> = if (jsonString.isNullOrEmpty()) {
            listOf()
        }

        else {
            try {
                Json {ignoreUnknownKeys = true}.decodeFromString<List<String>>(jsonString)
            } catch (e: Throwable) {
                e.printStackTrace()

                listOf()
            }
        }

        return achievementsUID
    }

    override suspend fun saveToDevice(quests: List<String>) {
        val jsonString = Json {ignoreUnknownKeys = true}.encodeToString(quests)
        preferenceStorage.putString(key = PREFS_NAME, value = jsonString)
    }


    private companion object {
        val PREFS_NAME = "achievements"
    }
}