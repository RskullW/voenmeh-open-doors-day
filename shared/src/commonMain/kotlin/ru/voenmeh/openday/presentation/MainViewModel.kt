package ru.voenmeh.openday.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
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

    private fun initializeAchievements() {
        val numbersOfFaculty = Constants.Numbers.numbersOfFaculty

        for (i in 0 until numbersOfFaculty) {
            val achievement = getAchievement(i)
        }
    }

    private fun getAchievement(value: Int): Achievement? {
        try {
            val faculty = Faculty.values().firstOrNull { it.value == value } ?: throw IllegalArgumentException("Faculty not founded")
            val title: String
            val description: String
            val urlImage: String
            val specialization: List<String>
            val link: String

            when (faculty) {
                Faculty.A -> {
                }

                Faculty.E -> {

                }
                else -> {

                }
            }
        }

        catch (e: Throwable) {
            return null
        }
    }

    private companion object {
        val PREFS_NAME =
    }
}