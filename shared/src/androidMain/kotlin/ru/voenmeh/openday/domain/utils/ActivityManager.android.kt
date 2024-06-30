package ru.voenmeh.openday.domain.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

actual object ActivityManager {
    actual val transferArgument: MutableMap<String, Any> = mutableMapOf()

    actual fun setActivity(activityProvider: Any, isResetActivity: Boolean, intent: Any?) {
        val context = activityProvider as? Context
        val appCompatActivity = activityProvider as? AppCompatActivity
        val androidIntent = intent as? Intent ?: Intent(context, appCompatActivity?.javaClass)

        if (isResetActivity) {
            androidIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context?.startActivity(androidIntent)
    }

    actual fun getActivityContext(activityProvider: Any): Any? {
        val context = activityProvider as? Context
        return when {
            context is Activity -> context
            context is ContextWrapper -> getActivityContext(context.baseContext)
            else -> null
        }
    }
}