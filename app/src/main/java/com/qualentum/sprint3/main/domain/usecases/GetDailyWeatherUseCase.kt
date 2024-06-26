package com.qualentum.sprint3.main.domain.usecases

import com.qualentum.sprint3.main.data.mappers.ListDays
import com.qualentum.sprint3.main.data.repository.remote.MainRepository
import javax.inject.Inject

class GetDailyWeatherUseCase @Inject constructor(private val weatherRepository: MainRepository) {
    suspend fun invoke(): Result<ListDays> {
        return weatherRepository.fetchDailyWeatherData()
    }
}