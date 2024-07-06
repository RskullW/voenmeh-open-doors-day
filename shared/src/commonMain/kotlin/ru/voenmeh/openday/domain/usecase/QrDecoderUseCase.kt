package ru.voenmeh.openday.domain.usecase

import ru.voenmeh.openday.domain.model.Quest
import ru.voenmeh.openday.domain.repository.QuestRepository

expect class QrDecoderUseCase {
    val questRepository: QuestRepository

    fun openCamera(onSuccess: (String) -> Unit)
    fun decodeQR(questJson: String): Quest
}