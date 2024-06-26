package com.qualentum.sprint3.main.domain.usecases

import com.qualentum.sprint3.main.data.mappers.CurrentWeather
import com.qualentum.sprint3.main.data.repository.remote.MainRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(private val weatherRepository: MainRepository) {
    suspend fun invoke(): Result<CurrentWeather> {
        return weatherRepository.fetchCurrentWeatherData()
    }
}