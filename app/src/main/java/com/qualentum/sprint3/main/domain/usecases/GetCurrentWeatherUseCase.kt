package com.qualentum.sprint3.main.domain.usecases

import com.qualentum.sprint3.main.data.mappers.CurrentWeather
import com.qualentum.sprint3.main.data.repository.remote.MainRepository

class GetCurrentWeatherUseCase(private val weatherRepository: MainRepository) {
    suspend fun invoke(): Result<CurrentWeather> {
        return weatherRepository.fetchCurrentWeatherData()
    }
}