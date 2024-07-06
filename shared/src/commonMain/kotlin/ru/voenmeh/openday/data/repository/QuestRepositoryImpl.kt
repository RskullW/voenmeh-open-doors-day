package ru.voenmeh.openday.data.repository

import kotlinx.serialization.json.Json
import ru.voenmeh.openday.domain.model.Quest
import ru.voenmeh.openday.domain.repository.QuestRepository

class QuestRepositoryImpl: QuestRepository {
    override fun decodeQuest(questJson: String): Quest {
        try {
            return Json{ ignoreUnknownKeys = true }.decodeFromString(string = questJson)
        } catch (e: Throwable) {
            return Quest()
        }
    }
}