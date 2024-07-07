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
import ru.voenmeh.openday.domain.utils.NativeHost

class QuestViewModel(val firebaseDB: FirebaseDB, val qrDecoderUseCase: QrDecoderUseCase, val achievementRepository: AchievementRepository): ViewModel() {
    val error: LiveData<String?> = LiveData(null)
    val isCorrectAnswer: LiveData<Boolean?> = LiveData(null)
    val toastMessage: LiveData<String?> = LiveData(null)
    val isStartQuest: LiveData<Boolean> = LiveData(false)

    val currentMistake: LiveData<Int> = LiveData(0)

    var currentQuest: Quest? = null
        private set

    private suspend fun openQuest(quest: Quest) {
        if (!isLocked(quest)) {
            withContextMain {
                toastMessage.update(value = Constants.Strings.repeatScanQr)
            }

            return
        }

        currentQuest = quest

        withContextMain {
            currentMistake.update(0)
            isStartQuest.update(true)
        }
    }

    private suspend fun isLocked(quest: Quest): Boolean {
        val questUids = achievementRepository.fromDevice()
        questUids.forEach { uid ->
            if (uid == quest.id) {
                return false
            }
        }

        return true
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
                    val mistake = currentMistake.getValue() ?: 0
                    currentMistake.update(mistake+1)
                    error.update(Constants.Strings.incorrectAnswer)
                }
            } else {
                saveUid(quest = currentQuest!!)

                withContextMain {
                    currentMistake.update(0)
                    isCorrectAnswer.update(value = true)
                }
            }

            withContextMain {
                updateStateScreen(StateScreen.DEFAULT)
            }
        }
    }

    public suspend fun saveUid(quest: Quest) {
        val quests = achievementRepository.fromDevice().toMutableList()
        quest.achievement?.isEnabled = true

        val foundedAchievement = quests.find { it == quest.id }

        if (foundedAchievement != null) {
            quests.remove(foundedAchievement)
        }

        quests.add(quest.id ?: "")

        achievementRepository.saveToDevice(quests)
    }

    public fun openQR() {
        isStartQuest.update(false)

        qrDecoderUseCase.openCamera { value ->
            val result = firebaseDB.post(value)
            CoroutineScope(Dispatchers.IO).launch {
                if (result.isEmpty()) {
                    withContextMain {
                        toastMessage.update(Constants.Strings.incorrectQr)
                    }
                    return@launch
                }
                val quest = result.first()

                val faculty = getFaculty(quest.id ?: "")

                if (faculty == null) {
                    withContextMain {
                        toastMessage.update(Constants.Strings.incorrectQr)
                    }

                    return@launch
                }

                quest.achievement?.faculty = faculty
                openQuest(quest)
            }
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