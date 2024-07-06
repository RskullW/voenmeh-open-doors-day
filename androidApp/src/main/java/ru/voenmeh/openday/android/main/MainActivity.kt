package ru.voenmeh.openday.android.main

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.voenmeh.openday.android.R
import ru.voenmeh.openday.android.dialog.showDialogError
import ru.voenmeh.openday.android.dialog.showDialogFaculty
import ru.voenmeh.openday.domain.constants.Constants
import ru.voenmeh.openday.domain.enums.StateScreen
import ru.voenmeh.openday.domain.model.Achievement
import ru.voenmeh.openday.presentation.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViewModel()
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
                    showDialogError(activity = this@MainActivity, description = Constants.Strings.unexpectedMessage)
                    viewModel.updateStateScreen(value = StateScreen.DEFAULT)
                }
            }
        }

        viewModel.achievements.addObserver { value ->
            if (value.isNullOrEmpty()) return@addObserver

            background.removeAllViews()

            value.forEach { achievement ->
                val achievementCard = createAchievementCard(achievement = achievement)
                background.addView(achievementCard)
            }

        }
    }

    private fun createAchievementCard(achievement: Achievement): AchievementCard {
        val achievementCard = AchievementCard(achievement = achievement, context = this@MainActivity)

        achievementCard.setOnClickListener {
            val isUnlocked = achievement.isEnabled

            if (isUnlocked) {
                showDialogFaculty(activity = this@MainActivity, achievement = achievement)
            }

            else {
                showDialogError(this@MainActivity, title = Constants.Strings.Achievement.lockedTitle, description = Constants.Strings.Achievement.lockedDescription, close = Constants.Strings.Common.ok)
            }
        }

        return achievementCard
    }
}