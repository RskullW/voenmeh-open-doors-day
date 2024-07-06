package ru.voenmeh.openday.android.dialog

import android.app.Activity
import android.app.Dialog
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ru.voenmeh.openday.android.R
import ru.voenmeh.openday.domain.constants.Constants
import ru.voenmeh.openday.domain.enums.Faculty
import ru.voenmeh.openday.domain.enums.QuestType
import ru.voenmeh.openday.domain.model.Quest

class DialogCorrectAnswer(private val activity: Activity, quest: Quest, onClose: () -> Unit = {}): LinearLayout(activity) {
    init {
        LayoutInflater.from(context).inflate(R.layout.dialog_correct_answer, this, true)

        initializeTitle(quest = quest)
        initializeImage(quest = quest)

        initializeButtons(onClose = onClose)
    }

    private fun initializeTitle(quest: Quest) {
        val textView = findViewById<TextView>(R.id.correctAnswer_textView_faculty)
        val text = "${quest.achievement?.faculty}"

        textView.text = text
    }

    private fun initializeImage(quest: Quest) {
        val imageView = findViewById<ImageView>(R.id.correctAnswer_imageView_achievement)

        val resource = when(quest.achievement?.faculty) {
            Faculty.A -> {
                R.drawable.ic_a_unlocked
            }

            Faculty.E -> {
                R.drawable.ic_e_unlocked
            }

            Faculty.I -> {
                R.drawable.ic_i_unlocked
            }

            Faculty.O -> {
                R.drawable.ic_o_unlocked
            }

            Faculty.R -> {
                R.drawable.ic_r_unlocked
            }

            Faculty.SPO -> {
                R.drawable.ic_spo_unlocked
            }

            else -> {
                R.drawable.ic_vuc_unlocked
            }
        }

        imageView.setImageResource(resource)
    }

    private fun initializeButtons(onClose: () -> Unit) {
        val close = findViewById<ImageView>(R.id.correctAnswer_imageView_close)
        val rootView = findViewById<LinearLayout>(R.id.correctAnswer_linearLayout)

        close.setOnClickListener {
            onClose()
        }

        rootView.setOnClickListener {
            close.callOnClick()
        }
    }

}

fun Dialog.showDialogCorrectAnswer(activity: Activity, quest: Quest, onAction: () -> Unit = {}) {
    val dialogCorrectAnswer = DialogCorrectAnswer(activity = activity, quest = quest) {
        dismiss()
        onAction()
    }

    setContentView(dialogCorrectAnswer)
    show()
}