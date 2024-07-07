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
import ru.voenmeh.openday.domain.repository.FirebaseDB
import ru.voenmeh.openday.domain.utils.Log
import ru.voenmeh.openday.domain.utils.NativeHost

class MainViewModel(val firebaseDB: FirebaseDB, val achievementRepository: AchievementRepository, val configParams: ConfigParams): ViewModel() {
    val achievements: LiveData<List<Achievement>> = LiveData(emptyList())
    val achievementsTitle: LiveData<String> = LiveData(Constants.Strings.Achievement.achievemet)
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
        var currentUnlockedAchievements: Int = 0
        val achievementTitle: String

        for (i in 0 until numbersOfFaculty) {
            val achievement = getAchievement(i) ?: continue

            if (achievement.isEnabled == true) {
                currentUnlockedAchievements++
            }

            achievements.add(achievement)
        }

         achievementTitle = "${Constants.Strings.Achievement.achievemet} ($currentUnlockedAchievements/$numbersOfFaculty)"

        withContextMain {
            this@MainViewModel.achievementsTitle.update(value = achievementTitle)
            this@MainViewModel.achievements.update(value = achievements)
        }
    }

    private suspend fun getAchievement(value: Int): Achievement? {
        try {
            val questsUnlocked = achievementRepository.fromDevice()
            val faculty =
                Faculty.entries.firstOrNull { it.value == value } ?: throw IllegalArgumentException(
                    "Faculty not founded"
                )

            val faculties = NativeHost.getUids()
            val currentUid = faculties[value]
            val foundedUid = questsUnlocked.find { it == currentUid } ?: return Achievement(faculty = faculty, isEnabled = false)
            val foundedAchievement = firebaseDB.post(uid = foundedUid).firstOrNull()?.achievement ?: Achievement(faculty = faculty, isEnabled = false)

            foundedAchievement.faculty = faculty
            foundedAchievement.isEnabled = true

            return foundedAchievement
        }

        catch (e: Throwable) {
            return null
        }
    }
}