package ru.voenmeh.openday.data.repository

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import ru.voenmeh.openday.domain.model.Achievement
import ru.voenmeh.openday.domain.model.Quest
import ru.voenmeh.openday.domain.repository.FirebaseDB
import ru.voenmeh.openday.domain.utils.Log

actual class FirebaseDBImpl(context: Context) : FirebaseDB {
    private val dataBase: FirebaseFirestore

    init {
        FirebaseApp.initializeApp(context)

        dataBase = FirebaseFirestore.getInstance()
    }

    actual override fun post(uid: String): List<Quest> {
        return runBlocking {
            val quest = getQuest(uid)
            if (quest != null) {
                val achievement = getAchievement(uid)
                if (achievement != null) {
                    quest.achievement = achievement
                }
            }
            listOfNotNull(quest)
        }
    }

    private suspend fun getQuest(uid: String): Quest? {
        return try {
            val document = dataBase.collection("FACULTY").document(uid).get().await()
            document.toObject<Quest>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private suspend fun getAchievement(uid: String): Achievement? {
        return try {
            val achievementsSnapshot = dataBase.collection("FACULTY").document(uid).collection("achievement").document(uid).get().await()

            Log("FACULTY", achievementsSnapshot.toObject<Achievement>().toString())
            achievementsSnapshot.toObject<Achievement>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}