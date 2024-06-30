package ru.voenmeh.openday.data.infrastructure

import android.content.Context
import android.content.SharedPreferences
import ru.voenmeh.openday.domain.utils.NativeHost

actual class PreferenceStorage(val context: Context) {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    }

    actual suspend fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    actual suspend fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    actual suspend fun clearItem(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    actual suspend fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        val prefsName: String = NativeHost.getPrefsName()
    }
}