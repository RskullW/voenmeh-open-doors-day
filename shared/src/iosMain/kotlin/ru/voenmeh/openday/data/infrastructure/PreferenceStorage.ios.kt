package ru.voenmeh.openday.data.infrastructure

import platform.Foundation.NSUserDefaults

actual class PreferenceStorage {
    actual suspend fun getString(key: String): String? {
        val userDefaults = NSUserDefaults.standardUserDefaults()
        val data = userDefaults.stringForKey(key) ?: return null
        return data
    }

    actual suspend fun putString(key: String, value: String) {
        val userDefaults = NSUserDefaults.standardUserDefaults()
        userDefaults.setObject(value, forKey = key)
        userDefaults.synchronize()
    }

    actual suspend fun clearItem(key: String) {
        val userDefaults = NSUserDefaults.standardUserDefaults()
        userDefaults.removeObjectForKey(key)
        userDefaults.synchronize()
    }

    actual suspend fun clearAll() {
    }
}