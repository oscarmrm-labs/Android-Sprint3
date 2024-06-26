package com.qualentum.sprint3.di

import com.qualentum.sprint3.common.data.Constants
import com.qualentum.sprint3.common.data.OpenMeteoClient
import com.qualentum.sprint3.main.data.repository.remote.MainRepository
import com.qualentum.sprint3.main.domain.usecases.GetCurrentWeatherUseCase
import com.qualentum.sprint3.main.domain.usecases.GetDailyWeatherUseCase
import com.qualentum.sprint3.main.ui.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideMainViewModel(
        getDailyWeatherUseCase: GetDailyWeatherUseCase,
        getCurrentWeatherUseCase: GetCurrentWeatherUseCase
    ): MainViewModel {
        return MainViewModel(
            getCurrentWeatherUseCase,
            getDailyWeatherUseCase
        )
    }

    @Provides
    @Singleton
    fun provideMainRepository(): MainRepository {
        return MainRepository(
            OpenMeteoClient.mainService,
            Constants.LATITUDE,
            Constants.LONGITUDE,
        )
    }

    @Provides
    @Singleton
    fun provideGetCurrentWeatherUseCase(repository: MainRepository): GetCurrentWeatherUseCase {
        return GetCurrentWeatherUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetDailyWeatherUseCase(mainRepository: MainRepository): GetDailyWeatherUseCase {
        return GetDailyWeatherUseCase(mainRepository)
    }
}