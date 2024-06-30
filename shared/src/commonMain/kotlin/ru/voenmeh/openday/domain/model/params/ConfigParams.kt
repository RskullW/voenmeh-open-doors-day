package ru.voenmeh.openday.domain.model.params

import ru.voenmeh.openday.data.infrastructure.PreferenceStorage

data class ConfigParams (
    val preferenceStorage: PreferenceStorage,
)