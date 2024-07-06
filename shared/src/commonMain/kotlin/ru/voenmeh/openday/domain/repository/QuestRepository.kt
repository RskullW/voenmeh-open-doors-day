package ru.voenmeh.openday.domain.repository

import ru.voenmeh.openday.domain.model.Quest

interface QuestRepository {
    fun decodeQuest(questJson: String): Quest
}