package ru.voenmeh.openday.domain.utils

import ru.voenmeh.openday.BuildConfig

private external fun prefsName(): String
actual object NativeHost {
    init {
        System.loadLibrary("api-keys")
    }
    actual fun getPrefsName(): String {
        return prefsName()
    }

    actual fun getUids(): Map<Int, String> {
        val faculties: MutableMap<Int, String> = mutableMapOf()

        faculties[0] = BuildConfig.FACULTY_A
        faculties[1] = BuildConfig.FACULTY_E
        faculties[2] = BuildConfig.FACULTY_I
        faculties[3] = BuildConfig.FACULTY_O
        faculties[4] = BuildConfig.FACULTY_R
        faculties[5] = BuildConfig.FACULTY_SPO
        faculties[6] = BuildConfig.FACULTY_VUC
        return faculties
    }

}