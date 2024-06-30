package ru.voenmeh.openday.domain.model

import kotlinx.serialization.Serializable
import ru.voenmeh.openday.domain.enums.Faculty

@Serializable
data class Achievement(
    val faculty: Faculty,
    val title: String,
    val description: String,
    val urlImage: String,
    val specializations: List<String>,
    val link: String,
    val isEnabled: Boolean
)