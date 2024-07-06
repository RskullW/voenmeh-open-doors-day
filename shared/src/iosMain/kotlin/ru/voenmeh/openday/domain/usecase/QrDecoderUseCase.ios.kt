package ru.voenmeh.openday.domain.usecase

import ru.voenmeh.openday.domain.model.Quest
import ru.voenmeh.openday.domain.repository.QuestRepository

actual class QrDecoderUseCase {
    actual val questRepository: QuestRepository
        get() = TODO("Not yet implemented")

    actual fun openCamera(onSuccess: (String) -> Unit) {
    }

    actual fun decodeQR(questJson: String): Quest {
        TODO("Not yet implemented")
    }

}