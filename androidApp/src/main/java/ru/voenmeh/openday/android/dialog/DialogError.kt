package ru.voenmeh.openday.android.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import ru.voenmeh.openday.android.R
import ru.voenmeh.openday.domain.constants.Constants

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

fun showDialogError(activity: Activity, title: String = Constants.Strings.titleError, description: String, close: String = Constants.Strings.Common.close, onAction: () -> Unit = {}) {
        val dialog = Dialog(activity, R.style.BottomSheetDialogTransparent)

        val dialogError = DialogError(activity) {
            dialog.dismiss()
            onAction()
        }

        dialogError.updateTitle(text = title)
        dialogError.updateDescription(text = description)
        dialogError.updateTextButton(text = close)

        dialog.setContentView(dialogError)
        dialog.show()
}