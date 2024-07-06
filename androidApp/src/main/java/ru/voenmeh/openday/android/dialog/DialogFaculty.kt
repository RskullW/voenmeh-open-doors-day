package ru.voenmeh.openday.android.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import ru.voenmeh.openday.android.R
import ru.voenmeh.openday.domain.model.Achievement
import ru.voenmeh.openday.domain.utils.Log

class DialogFaculty(private val activity: Activity, achievement: Achievement, onClose: () -> Unit = {}): LinearLayout(activity) {
    init {
        LayoutInflater.from(context).inflate(R.layout.dialog_faculty, this, true)

        initializeTitle(achievement = achievement)
        initializeDescription(achievement = achievement)
        initializeDepartment(achievement = achievement)
        initializeImage(achievement = achievement)

        initializeButtons(achievement = achievement, onClose = onClose)
    }

    private fun initializeTitle(achievement: Achievement) {
        val textView = findViewById<TextView>(R.id.dialogFaculty_textView_title)
        textView.text = achievement.title
    }

    private fun initializeDescription(achievement: Achievement) {
        val textView = findViewById<TextView>(R.id.dialogFaculty_textView_description)
        textView.text = achievement.description
    }

    private fun initializeDepartment(achievement: Achievement) {
        val textView = findViewById<TextView>(R.id.dialogFaculty_textView_department)

        var text: String = ""

        achievement.specializations.forEach { value ->
            text+="$value\n"
        }

        textView.text = text
    }

    private fun initializeImage(achievement: Achievement) {
        val imageView = findViewById<ShapeableImageView>(R.id.dialogFaculty_shapeableImageView)
        val url = achievement.urlImage

        Log("URLIMAGE", url)
        Picasso.get()
            .load(url).placeholder(R.drawable.placeholder_image)
            .into(imageView)
    }

    private fun initializeButtons(achievement: Achievement, onClose: () -> Unit) {
        val close = findViewById<ImageView>(R.id.dialogFaculty_imageView_close)
        val rootView = findViewById<LinearLayout>(R.id.dialogFaculty_linearLayout)
        val button = findViewById<Button>(R.id.dialogFaculty_button_continue)

        close.setOnClickListener {
            onClose()
        }

        rootView.setOnClickListener {
            close.callOnClick()
        }

        button.setOnClickListener {
            close.callOnClick()
            openUrl(Uri.parse(achievement.link))
        }

    }

    private fun openUrl(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        activity.startActivity(intent)
    }
}

fun showDialogFaculty(activity: Activity, achievement: Achievement) {
        val dialog = Dialog(activity, R.style.BottomSheetDialogTransparent)

        val dialogError = DialogFaculty(activity = activity, achievement = achievement) {
            dialog.dismiss()
        }

        dialog.setContentView(dialogError)
        dialog.show()
}