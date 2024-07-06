package ru.voenmeh.openday.domain.model

import kotlinx.serialization.Serializable
import ru.voenmeh.openday.domain.enums.Faculty

@Serializable
data class Achievement(
    var faculty: Faculty = Faculty.A,
    val title: String = "",
    val description: String = "",
    val urlImage: String = "",
    val departments: List<String> = emptyList(),
    val link: String = "",
    var isEnabled: Boolean? = false
)