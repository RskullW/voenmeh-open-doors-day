package ru.voenmeh.openday.domain.utils

expect object NativeHost {
    fun getPrefsName(): String
    fun getUids(): Map<Int, String>
}