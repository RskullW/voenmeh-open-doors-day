package ru.voenmeh.openday.data.repository

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.voenmeh.openday.data.infrastructure.PreferenceStorage
import ru.voenmeh.openday.domain.model.Achievement
import ru.voenmeh.openday.domain.repository.AchievementRepository

class AchievementRepositoryImpl(private val preferenceStorage: PreferenceStorage): AchievementRepository {
    override suspend fun fromDevice(): List<Achievement> {
        val jsonString = preferenceStorage.getString(PREFS_NAME)

        val achievements: List<Achievement> = if (jsonString.isNullOrEmpty()) {
            listOf()
        }

        else {
            try {
                Json {ignoreUnknownKeys = true}.decodeFromString<List<Achievement>>(jsonString)
            } catch (e: Throwable) {
                e.printStackTrace()

                listOf()
            }
        }

        return achievements
    }

    override suspend fun saveToDevice(achievements: List<Achievement>) {
        val jsonString = Json {ignoreUnknownKeys = true}.encodeToString(achievements)
        preferenceStorage.putString(key = PREFS_NAME, value = jsonString)
    }


    private companion object {
        val PREFS_NAME = "achievements"
    }
}