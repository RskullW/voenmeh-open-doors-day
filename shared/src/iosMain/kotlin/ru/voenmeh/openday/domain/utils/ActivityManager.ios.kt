package ru.voenmeh.openday.domain.utils

actual object ActivityManager {
    actual val transferArgument: MutableMap<String, Any>
        get() = TODO("Not yet implemented")

    actual fun setActivity(
        activityProvider: Any,
        isResetActivity: Boolean,
        intent: Any?,
    ) {
    }

    actual fun getActivityContext(activityProvider: Any): Any? {
        TODO("Not yet implemented")
    }
}