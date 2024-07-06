package ru.voenmeh.openday.android.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import ru.voenmeh.openday.android.R
import ru.voenmeh.openday.domain.constants.Constants
import ru.voenmeh.openday.domain.enums.QuestType
import ru.voenmeh.openday.domain.model.Achievement
import ru.voenmeh.openday.domain.model.Quest
import ru.voenmeh.openday.domain.utils.Log

class DialogQuest(private val activity: Activity, quest: Quest, onClose: () -> Unit = {}): LinearLayout(activity) {
    init {
        LayoutInflater.from(context).inflate(R.layout.dialog_quest, this, true)

        initializeTitle(quest = quest)
        initializeQuestDescription(quest = quest)
        initializeHint(quest = quest)
        initializeTypeInput(quest = quest)

        initializeButtons(onClose = onClose)
    }

    private fun initializeTitle(quest: Quest) {
        val textView = findViewById<TextView>(R.id.dialogQuest_textView_faculty)
        val text = "${Constants.Strings.Common.by.lowercase()} ${quest.achievement?.faculty}"

        textView.text = text
    }

    private fun initializeQuestDescription(quest: Quest) {
        val textView = findViewById<TextView>(R.id.dialogQuest_textView_quest)
        val text = quest.mission

        textView.text = text
    }

    private fun initializeHint(quest: Quest) {
        val hintView = findViewById<TextView>(R.id.dialogQuest_textView_hint)
        val hintText = findViewById<TextView>(R.id.dialogQuest_textView_hintMessage)

        hintText.text = quest.hint

        hintView.setOnClickListener {
            setHintVisibility(if (hintText.visibility == View.GONE) View.VISIBLE else View.GONE)
        }
    }

    private fun initializeTypeInput(quest: Quest) {
        val textInputEditText = findViewById<TextInputEditText>(R.id.dialogQuest_textInputEditText)
        val questType = QuestType.fromInt(quest.questType ?: 1)

        textInputEditText.inputType = when (questType) {
            QuestType.WORD -> {
                textInputEditText.maxEms = Constants.Numbers.maxEmsWordQuest
                InputType.TYPE_CLASS_TEXT
            }

            QuestType.NUMBER -> {
                textInputEditText.maxEms = Constants.Numbers.maxNumberQuest
                InputType.TYPE_CLASS_NUMBER
            }

            else -> {
                textInputEditText.maxEms = Constants.Numbers.maxEmsWordQuest

                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
            }
        }

        textInputEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                updateErrorText(null)
            }

        })
    }

    private fun initializeButtons(onClose: () -> Unit) {
        val close = findViewById<ImageView>(R.id.dialogQuest_imageView_close)


        close.setOnClickListener {
            onClose()
        }

    }

    public fun updateErrorText(errorText: String?) {
        val textInputLinearLayout = findViewById<TextInputLayout>(R.id.dialogQuest_textInputLayout)

        textInputLinearLayout.isErrorEnabled = !errorText.isNullOrEmpty()
        textInputLinearLayout.error = errorText
    }

    public fun getText(): String {
        val textInputEditText = findViewById<TextInputEditText>(R.id.dialogQuest_textInputEditText)

        return textInputEditText.text?.toString() ?: ""
    }

    public fun onClose() {
        val close = findViewById<ImageView>(R.id.dialogQuest_imageView_close)

        close.callOnClick()
    }

    public fun setHintVisibility(view: Int) {
        val hintText = findViewById<TextView>(R.id.dialogQuest_textView_hintMessage)

        hintText.visibility = view
    }

    override fun setOnClickListener(l: OnClickListener?) {
        val button = findViewById<Button>(R.id.dialogQuest_button_check)

        button.setOnClickListener(l)
    }

}

fun Dialog.showDialogQuest(activity: Activity, quest: Quest): DialogQuest {
    val dialogQuest = DialogQuest(activity = activity, quest = quest) {
        dismiss()
    }

    setContentView(dialogQuest)
    show()

    return dialogQuest
}