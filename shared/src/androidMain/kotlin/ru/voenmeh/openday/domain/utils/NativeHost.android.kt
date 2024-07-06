package ru.voenmeh.openday.domain.utils

private external fun prefsName(): String
actual object NativeHost {
    init {
        System.loadLibrary("api-keys")
    }
    actual fun getPrefsName(): String {
        return prefsName()
    }

    actual fun getUrl(): String {
        TODO("Not yet implemented")
    }
}