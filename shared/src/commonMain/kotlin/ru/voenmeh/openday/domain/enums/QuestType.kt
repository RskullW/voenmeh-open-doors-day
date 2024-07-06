package ru.voenmeh.openday.domain.enums

import kotlinx.serialization.Serializable

@Serializable
enum class QuestType(val value: Int) {
    WORD(0),
    NUMBER(1),
    SENTENCE(2);

    companion object {
        fun fromInt(value: Int): QuestType {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("Unknown value: $value")
        }
    }
}