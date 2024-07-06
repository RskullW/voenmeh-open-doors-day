package ru.voenmeh.openday.data.repository

import ru.voenmeh.openday.domain.model.Quest
import ru.voenmeh.openday.domain.repository.FirebaseDB

actual class FirebaseDBImpl : FirebaseDB {
    actual override fun post(uid: String): List<Quest> {
        TODO("Not yet implemented")
    }

}