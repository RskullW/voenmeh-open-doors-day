package ru.voenmeh.openday.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.voenmeh.openday.domain.constants.Constants
import ru.voenmeh.openday.domain.enums.Faculty
import ru.voenmeh.openday.domain.enums.StateScreen
import ru.voenmeh.openday.domain.model.Achievement
import ru.voenmeh.openday.domain.model.params.ConfigParams

class MainViewModel(val configParams: ConfigParams): ViewModel() {
    val achievements: LiveData<List<Achievement>> = LiveData(emptyList())

    init {
        update()
    }

    private fun update() {
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

        this.achievements.update(value = achievements)
    }

    private suspend fun getAchievement(value: Int): Achievement? {
        try {
            val achievementsUnlocked = fromJsonAchievementsList()
            val faculty = Faculty.values().firstOrNull { it.value == value } ?: throw IllegalArgumentException("Faculty not founded")

            val title: String
            val description: String
            val urlImage: String
            val specializations: List<String>
            val link: String
            val isEnabled: Boolean = (achievementsUnlocked.find { it.faculty == faculty } != null)

            when (faculty) {
                Faculty.E -> {
                    title = Constants.Strings.facultyE
                    description = Constants.Strings.descriptionI
                    urlImage = Constants.Strings.urlImageI
                    specializations = Constants.Strings.specializationI
                    link = Constants.Strings.linkI
                }

                Faculty.I -> {
                    title = Constants.Strings.facultyI
                    description = Constants.Strings.descriptionI
                    urlImage = Constants.Strings.urlImageI
                    specializations = Constants.Strings.specializationI
                    link = Constants.Strings.linkI
                }

                Faculty.O -> {
                    title = Constants.Strings.facultyO
                    description = Constants.Strings.descriptionO
                    urlImage = Constants.Strings.urlImageO
                    specializations = Constants.Strings.specializationO
                    link = Constants.Strings.linkO
                }

                Faculty.R -> {
                    title = Constants.Strings.facultyR
                    description = Constants.Strings.descriptionR
                    urlImage = Constants.Strings.urlImageR
                    specializations = Constants.Strings.specializationR
                    link = Constants.Strings.linkR
                }

                Faculty.VUC -> {
                    title = Constants.Strings.facultyVUC
                    description = Constants.Strings.descriptionVUC
                    urlImage = Constants.Strings.urlImageVUC
                    specializations = Constants.Strings.specializationVUC
                    link = Constants.Strings.linkVUC
                }

                Faculty.SPO -> {
                    title = Constants.Strings.facultySPO
                    description = Constants.Strings.descriptionSPO
                    urlImage = Constants.Strings.urlImageSPO
                    specializations = Constants.Strings.specializationSPO
                    link = Constants.Strings.linkSPO
                }

                else -> {
                    title = Constants.Strings.facultyA
                    description = Constants.Strings.descriptionA
                    urlImage = Constants.Strings.urlImageA
                    specializations = Constants.Strings.specializationA
                    link = Constants.Strings.linkA
                }
            }

            return Achievement(
                faculty = faculty,
                title = title,
                description = description,
                urlImage = urlImage,
                specializations = specializations,
                link = link,
                isEnabled = isEnabled
            )
        }

        catch (e: Throwable) {
            return null
        }
    }

    private suspend fun fromJsonAchievementsList(): List<Achievement> {
        val jsonString = configParams.preferenceStorage.getString(PREFS_NAME)

        val achievements: List<Achievement> = if (jsonString.isNullOrEmpty()) {
            listOf()
        }

        else {
            try {
                Json.decodeFromString<List<Achievement>>(jsonString)
            } catch (e: Throwable) {
                e.printStackTrace()

                listOf()
            }
        }

        return achievements
    }

    public suspend fun saveAchievements(achievement: Achievement) {
        val achievements = fromJsonAchievementsList().toMutableList()
        achievements.add(achievement)

        val jsonString = Json.encodeToString(achievements)
        configParams.preferenceStorage.putString(key = PREFS_NAME, value = jsonString)
    }

    private companion object {
        val PREFS_NAME = "achievements"
    }
}