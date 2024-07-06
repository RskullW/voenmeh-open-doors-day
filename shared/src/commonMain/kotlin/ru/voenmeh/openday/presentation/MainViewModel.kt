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
import ru.voenmeh.openday.domain.model.params.ConfigParams
import ru.voenmeh.openday.domain.repository.AchievementRepository

class MainViewModel(val achievementRepository: AchievementRepository, val configParams: ConfigParams): ViewModel() {
    val achievements: LiveData<List<Achievement>> = LiveData(emptyList())

    init {
        update()
    }

    public fun update() {
        updateStateScreen(StateScreen.LOADING)

        CoroutineScope(Dispatchers.IO).launch {
            initializeAchievements()

            withContextMain {
                updateStateScreen(StateScreen.DEFAULT)
            }
        }
    }

    private suspend fun initializeAchievements() {
        val numbersOfFaculty = Constants.Numbers.numbersOfFaculty
        val achievements: MutableList<Achievement> = mutableListOf()

        for (i in 0 until numbersOfFaculty) {
            val achievement = getAchievement(i) ?: continue
            achievements.add(achievement)
        }

        withContextMain {
            this@MainViewModel.achievements.update(value = achievements)
        }
    }

    private suspend fun getAchievement(value: Int): Achievement? {
        try {
            val achievementsUnlocked = achievementRepository.fromDevice()
            val faculty =
                Faculty.entries.firstOrNull { it.value == value } ?: throw IllegalArgumentException(
                    "Faculty not founded"
                )

            return achievementsUnlocked.find { it.faculty == faculty }
                ?: Achievement(faculty = faculty, isEnabled = false)
        }

        catch (e: Throwable) {
            return null
        }
    }
}