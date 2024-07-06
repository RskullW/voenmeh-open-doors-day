package ru.voenmeh.openday.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Quest(
    val id: String? = "",
    val hint: String? = "",
    var achievement: Achievement? = null,
    val mission: String? = "",
    val questType: Int? = 1,
    val answer: String? = "",
)
