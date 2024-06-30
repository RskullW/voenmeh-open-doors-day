package ru.voenmeh.openday.android.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import ru.voenmeh.openday.android.R

class DialogError(context: Context, onClose: () -> Unit = {}): LinearLayout(context) {
    init {
        LayoutInflater.from(context).inflate(R.layout.dialog_error, this, true)

        val closeTextView = findViewById<TextView>(R.id.dialogError_textView_close)
        val rootView = findViewById<LinearLayout>(R.id.dialogError_linearLayout)

        closeTextView.setOnClickListener {
            onClose()
        }

        rootView.setOnClickListener {
            closeTextView.callOnClick()
        }
    }

    public fun updateTitle(text: String) {
        val textView = findViewById<TextView>(R.id.dialogError_textView_title)
        textView.text = text
    }

    public fun updateDescription(text: String) {
        val textView = findViewById<TextView>(R.id.dialogError_textView_description)
        textView.text = text
    }

    public fun updateTextButton(text: String) {
        val textView = findViewById<TextView>(R.id.dialogError_textView_close)
        textView.text = text

    }
}