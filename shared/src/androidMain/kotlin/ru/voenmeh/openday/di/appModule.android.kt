package ru.voenmeh.openday.di

import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import ru.voenmeh.openday.data.infrastructure.PreferenceStorage
import ru.voenmeh.openday.data.repository.AchievementRepositoryImpl
import ru.voenmeh.openday.data.repository.FirebaseDBImpl
import ru.voenmeh.openday.data.repository.QuestRepositoryImpl
import ru.voenmeh.openday.domain.model.params.ConfigParams
import ru.voenmeh.openday.domain.repository.AchievementRepository
import ru.voenmeh.openday.domain.repository.FirebaseDB
import ru.voenmeh.openday.domain.repository.QuestRepository
import ru.voenmeh.openday.domain.usecase.QrDecoderUseCase
import ru.voenmeh.openday.presentation.MainViewModel
import ru.voenmeh.openday.presentation.QuestViewModel

actual val singleModule: Module = module {
    single { PreferenceStorage(context = androidContext()) }
    single { ConfigParams(preferenceStorage = get())}
    single <AchievementRepository> { AchievementRepositoryImpl(preferenceStorage = get()) }
    single <QuestRepository> { QuestRepositoryImpl() }
    single <FirebaseDB> { FirebaseDBImpl(androidContext()) }
}

actual val factoryModule: Module = module {
    factory { (activity: AppCompatActivity) ->
        QrDecoderUseCase(activity = activity, questRepository = get())
    }
}

actual val viewModelModule: Module = module {
    viewModel { MainViewModel(firebaseDB = get(), achievementRepository = get(), configParams = get()) }
    viewModel { (activity: AppCompatActivity) ->
        QuestViewModel(firebaseDB = get(), qrDecoderUseCase = get { parametersOf(activity) }, achievementRepository = get())
    }
}