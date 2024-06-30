package ru.voenmeh.openday.presentation

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.LiveData

class LiveData <T> (default: T) {
    private val _something: MutableLiveData<T?> = MutableLiveData(default)
    private val something: LiveData<T?> = _something

    fun update(value: T?) {
        _something.value = value
    }

    fun getValue(): T? {
        return something.value
    }

    fun addObserver(observer: (T?) -> Unit) {
        something.addObserver(observer)
    }
}