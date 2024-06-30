package ru.voenmeh.openday.domain.utils

import ru.voenmeh.openday.domain.enums.TypeError

actual fun Log(
    title: String,
    message: String,
    typeError: TypeError
) {
    when(typeError) {
        TypeError.WARNING -> android.util.Log.w(title, message)
        TypeError.ERROR -> android.util.Log.e(title, message)
        else -> android.util.Log.d(title, message)
    }
}