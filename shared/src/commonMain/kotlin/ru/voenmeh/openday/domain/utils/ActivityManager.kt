package ru.voenmeh.openday.domain.utils

expect object ActivityManager {
    val transferArgument: MutableMap<String, Any>
    fun setActivity(activityProvider: Any, isResetActivity: Boolean = false, intent: Any? = null)
    fun getActivityContext(activityProvider: Any): Any?
}