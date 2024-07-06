package ru.voenmeh.openday.domain.repository

import ru.voenmeh.openday.domain.model.Quest

interface FirebaseDB {
    fun post(uid: String): List<Quest>
}