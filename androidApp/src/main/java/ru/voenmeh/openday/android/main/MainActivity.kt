package ru.voenmeh.openday.android.main

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.voenmeh.openday.android.R
import ru.voenmeh.openday.android.dialog.DialogQuest
import ru.voenmeh.openday.android.dialog.showDialogCorrectAnswer
import ru.voenmeh.openday.android.dialog.showDialogError
import ru.voenmeh.openday.android.dialog.showDialogFaculty
import ru.voenmeh.openday.android.dialog.showDialogQuest
import ru.voenmeh.openday.domain.constants.Constants
import ru.voenmeh.openday.domain.enums.StateScreen
import ru.voenmeh.openday.domain.model.Achievement
import ru.voenmeh.openday.domain.model.Quest
import ru.voenmeh.openday.presentation.MainViewModel
import ru.voenmeh.openday.presentation.QuestViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
    private val questViewModel: QuestViewModel by viewModel { parametersOf(this@MainActivity) }
    private var currentQuest: DialogQuest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViewModel()
        initializeQuestViewModel()
        initializeButton()
    }

    private fun initializeViewModel() {
        val background = findViewById<LinearLayout>(R.id.main_linearLayout_achievements)
        val stateDefault = findViewById<FrameLayout>(R.id.main_frameLayout_stateDefault)
        val stateLoading = findViewById<LinearLayout>(R.id.main_linearLayout_stateLoading)

        viewModel.stateScreen.addObserver { value ->
            if (value == null) return@addObserver

            when (value) {
                StateScreen.DEFAULT -> {
                    stateDefault.visibility = View.VISIBLE
                    stateLoading.visibility = View.GONE
                }

                StateScreen.LOADING -> {
                    stateDefault.visibility = View.GONE
                    stateLoading.visibility = View.VISIBLE
                }

                else -> {
                    val dialog = Dialog(this@MainActivity, R.style.BottomSheetDialogTransparent)
                    dialog.showDialogError(activity = this@MainActivity, description = Constants.Strings.unexpectedMessage)

                    viewModel.updateStateScreen(value = StateScreen.DEFAULT)
                }
            }
        }

        viewModel.achievements.addObserver { value ->
            if (value.isNullOrEmpty()) return@addObserver
            background.removeAllViews()

            val list = value.toMutableList()

            list.sortBy { it.isEnabled == false }
            list.forEach { achievement ->
                val achievementCard = createAchievementCard(achievement = achievement)
                background.addView(achievementCard)
            }
        }
    }

    private fun initializeButton() {
        val button = findViewById<Button>(R.id.main_button_continue)

        button.setOnClickListener {
            questViewModel.openQR()
        }
    }
    private fun initializeQuestViewModel() {
        questViewModel.stateScreen.addObserver { value ->
            if (value == null || currentQuest == null) return@addObserver

            val stateDefault = currentQuest?.findViewById<LinearLayout>(R.id.dialogQuest_linearLayout_stateDefault)
            val stateLoading = currentQuest?.findViewById<LinearLayout>(R.id.dialogQuest_linearLayout_stateLoading)

            when (value) {
                StateScreen.LOADING -> {
                    stateDefault?.visibility = View.GONE
                    stateLoading?.visibility = View.VISIBLE
                }

                else -> {
                    stateDefault?.visibility = View.VISIBLE
                    stateLoading?.visibility = View.GONE
                }
            }
        }

        questViewModel.error.addObserver { value ->
            if (currentQuest == null) return@addObserver

            currentQuest?.updateErrorText(errorText = value)
        }

        questViewModel.isCorrectAnswer.addObserver { value ->
            if (value == null || !value || currentQuest == null) return@addObserver

            currentQuest?.onClose()

            val dialog = Dialog(this@MainActivity, R.style.BottomSheetDialogTransparent)
            dialog.showDialogCorrectAnswer(this@MainActivity, quest = questViewModel.currentQuest ?: Quest()) {
                viewModel.update()
            }
        }

        questViewModel.isStartQuest.addObserver { value ->
            if (value == null || !value) return@addObserver
            val quest = questViewModel.currentQuest ?: return@addObserver

            showQuest(quest = quest)

        }
        questViewModel.toastMessage.addObserver { value ->
            if (value.isNullOrEmpty()) return@addObserver

            Toast.makeText(this@MainActivity, value, Toast.LENGTH_SHORT).show()
        }
    }

    private fun createAchievementCard(achievement: Achievement): AchievementCard {
        val achievementCard = AchievementCard(achievement = achievement, context = this@MainActivity)

        achievementCard.setOnClickListener {
            val dialog = Dialog(this@MainActivity, R.style.BottomSheetDialogTransparent)
            val isUnlocked = achievement.isEnabled ?: false

            if (isUnlocked) {
                dialog.showDialogFaculty(activity = this@MainActivity, achievement = achievement)
            }

            else {
                dialog.showDialogError(this@MainActivity, title = Constants.Strings.Achievement.lockedTitle, description = Constants.Strings.Achievement.lockedDescription, close = Constants.Strings.Common.ok)
            }
        }

        return achievementCard
    }

    public fun showQuest(quest: Quest) {
        val dialog = Dialog(this@MainActivity, R.style.BottomSheetDialogTransparent)
        currentQuest = dialog.showDialogQuest(activity = this@MainActivity, quest = quest)

        currentQuest?.setOnClickListener {
            questViewModel.checkAnswer(currentQuest?.getText() ?: "")
        }
    }
}