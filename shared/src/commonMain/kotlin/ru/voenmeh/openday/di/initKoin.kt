package ru.voenmeh.openday.di

import org.koin.dsl.KoinAppDeclaration

public fun startKoin(koinAppDeclaration: KoinAppDeclaration) {
    org.koin.core.context.startKoin {
        koinAppDeclaration()
        modules(listOf(singleModule, factoryModule, viewModelModule))
    }
}