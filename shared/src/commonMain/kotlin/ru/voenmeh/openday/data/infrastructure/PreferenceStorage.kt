package ru.voenmeh.openday.data.infrastructure

expect class PreferenceStorage {
    suspend fun getString(key: String): String?
    suspend fun putString(key: String, value: String)
    suspend fun clearItem(key: String)
    suspend fun clearAll()
}