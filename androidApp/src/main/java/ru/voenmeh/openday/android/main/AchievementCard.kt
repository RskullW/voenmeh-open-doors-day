package ru.voenmeh.openday.android.main

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import ru.voenmeh.openday.android.R
import ru.voenmeh.openday.android.dialog.showDialogError
import ru.voenmeh.openday.domain.enums.Faculty
import ru.voenmeh.openday.domain.model.Achievement

class AchievementCard(private val achievement: Achievement, context: Context): FrameLayout(context) {
    init {
        LayoutInflater.from(context).inflate(R.layout.frame_card, this, true)

        initializeImage()
    }

    private fun initializeImage() {
        val imageView = findViewById<ImageView>(R.id.card_imageView)

        val resource = when(achievement.faculty) {
            Faculty.A -> {
                if (achievement.isEnabled == true) {
                    R.drawable.ic_a_unlocked
                }

                else {
                    R.drawable.ic_a_lock
                }
            }

            Faculty.E -> {
                if (achievement.isEnabled == true) {
                    R.drawable.ic_e_unlocked
                }

                else {
                    R.drawable.ic_e_lock
                }
            }

            Faculty.I -> {
                if (achievement.isEnabled == true) {
                    R.drawable.ic_i_unlocked
                }

                else {
                    R.drawable.ic_i_lock
                }
            }

            Faculty.O -> {
                if (achievement.isEnabled == true) {
                    R.drawable.ic_o_unlocked
                }

                else {
                    R.drawable.ic_o_lock
                }
            }

            Faculty.R -> {
                if (achievement.isEnabled == true) {
                    R.drawable.ic_r_unlocked
                }

                else {
                    R.drawable.ic_r_lock
                }
            }

            Faculty.VUC -> {
                if (achievement.isEnabled == true) {
                    R.drawable.ic_vuc_unlocked
                }

                else {
                    R.drawable.ic_vuc_lock
                }
            }

            Faculty.SPO -> {
                if (achievement.isEnabled == true) {
                    R.drawable.ic_spo_unlocked
                }

                else {
                    R.drawable.ic_spo_lock
                }
            }
        }

        imageView.setImageResource(resource)
    }
}