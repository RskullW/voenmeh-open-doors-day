package ru.voenmeh.openday.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.voenmeh.openday.data.infrastructure.PreferenceStorage
import ru.voenmeh.openday.domain.model.params.ConfigParams
import ru.voenmeh.openday.presentation.MainViewModel

actual val singleModule: Module = module {
    single { PreferenceStorage(context = androidContext()) }
    single { ConfigParams(preferenceStorage = get())}
}

actual val factoryModule: Module = module {

}

actual val viewModelModule: Module = module {
    viewModel { MainViewModel(configParams = get()) }
}