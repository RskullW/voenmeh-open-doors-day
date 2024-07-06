package ru.voenmeh.openday.data.repository

import ru.voenmeh.openday.domain.model.Quest
import ru.voenmeh.openday.domain.repository.FirebaseDB

expect class FirebaseDBImpl: FirebaseDB {
    override fun post(uid: String): List<Quest>
}