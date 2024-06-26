package com.qualentum.sprint3.di

import androidx.lifecycle.SavedStateHandle
import com.qualentum.sprint3.common.data.Constants
import com.qualentum.sprint3.common.data.OpenMeteoClient
import com.qualentum.sprint3.detail.data.repository.remote.DetailMeteoAPIService
import com.qualentum.sprint3.detail.data.repository.remote.DetailRepository
import com.qualentum.sprint3.detail.domain.usecases.GetDetailWeatherUseCase
import com.qualentum.sprint3.detail.ui.DetailViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailModule {

    @Provides
    @Singleton
    fun provideDetailViewModel(
        getDailyWeatherUseCase: GetDetailWeatherUseCase
    ): DetailViewModel {
        return DetailViewModel(
            getDailyWeatherUseCase,
            SavedStateHandle()
        )
    }

    @Provides
    @Singleton
    fun provideDetailAPIService(): DetailMeteoAPIService {
        return OpenMeteoClient.instanceDetailService()
    }

    @Provides
    @Singleton
    fun provideDetailRepository(openMeteoAPIService: DetailMeteoAPIService): DetailRepository {
        return DetailRepository(
            openMeteoAPIService,
            Constants.LATITUDE,
            Constants.LONGITUDE,
        )
    }

    @Provides
    @Singleton
    fun provideGetDetailWeatherUseCase(repository: DetailRepository): GetDetailWeatherUseCase {
        return GetDetailWeatherUseCase(repository)
    }
}

