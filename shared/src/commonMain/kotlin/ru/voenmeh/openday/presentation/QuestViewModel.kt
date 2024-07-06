package ru.voenmeh.openday.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import ru.voenmeh.openday.domain.constants.Constants
import ru.voenmeh.openday.domain.enums.Faculty
import ru.voenmeh.openday.domain.enums.StateScreen
import ru.voenmeh.openday.domain.model.Achievement
import ru.voenmeh.openday.domain.model.Quest
import ru.voenmeh.openday.domain.repository.AchievementRepository
import ru.voenmeh.openday.domain.repository.FirebaseDB
import ru.voenmeh.openday.domain.usecase.QrDecoderUseCase
import ru.voenmeh.openday.domain.utils.Log
import ru.voenmeh.openday.domain.utils.NativeHost

class QuestViewModel(val firebaseDB: FirebaseDB, val qrDecoderUseCase: QrDecoderUseCase, val achievementRepository: AchievementRepository): ViewModel() {
    val error: LiveData<String?> = LiveData(null)
    val isCorrectAnswer: LiveData<Boolean?> = LiveData(null)
    val toastMessage: LiveData<String?> = LiveData(null)
    val isStartQuest: LiveData<Boolean> = LiveData(false)

    var currentQuest: Quest? = null
        private set

    public fun openQuest(quest: Quest) {
        currentQuest = quest
        isStartQuest.update(true)
    }

    public fun checkAnswer(answerUser: String) {
        updateStateScreen(StateScreen.LOADING)

        if (currentQuest == null || currentQuest?.achievement == null) {
            error.update(Constants.Strings.titleError)
            updateStateScreen(StateScreen.DEFAULT)
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val answer = currentQuest?.answer ?: ""

            val answerFormatted = answer.replace(" ", "").lowercase()
            val answerUserFormatted = answerUser.replace(" ", "").lowercase()

            if (answerFormatted != answerUserFormatted) {
                withContextMain {
                    error.update(Constants.Strings.incorrectAnswer)
                }
            } else {
                saveAchievements(achievement = currentQuest!!.achievement!!)

                withContextMain {
                    isCorrectAnswer.update(value = true)
                }
            }

            withContextMain {
                updateStateScreen(StateScreen.DEFAULT)
            }
        }
    }

    public suspend fun saveAchievements(achievement: Achievement) {
        val achievements = achievementRepository.fromDevice().toMutableList()
        achievement.isEnabled = true

        val foundedAchievement = achievements.find { it.faculty == achievement.faculty }

        if (foundedAchievement != null) {
            achievements.remove(foundedAchievement)
        }

        achievements.add(achievement)

        achievementRepository.saveToDevice(achievements)
    }

    public fun openQR() {
        isStartQuest.update(false)

        qrDecoderUseCase.openCamera { value ->
            val result = firebaseDB.post(value)

            if (result.isEmpty()) {
                toastMessage.update(Constants.Strings.incorrectQr)
                return@openCamera
            }
            val quest = result.first()

            val faculty = getFaculty(quest.id ?: "")

            if (faculty == null) {
                toastMessage.update(Constants.Strings.incorrectQr)
                return@openCamera
            }

            quest.achievement?.faculty = faculty
            openQuest(quest)
        }
    }

    private fun getFaculty(id: String): Faculty? {
        NativeHost.getUids().forEach {
            if (it.value == id) {
                return Faculty.fromInt(it.key)
            }
        }

        return null
    }
}