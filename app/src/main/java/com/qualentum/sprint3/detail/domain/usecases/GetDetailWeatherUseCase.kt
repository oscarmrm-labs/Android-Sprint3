package com.qualentum.sprint3.detail.domain.usecases

import com.qualentum.sprint3.detail.data.mappers.DetailWeather
import com.qualentum.sprint3.detail.data.repository.remote.DetailRepository


class GetDetailWeatherUseCase(private val detailRepository: DetailRepository) {
    suspend operator fun invoke(): Result<DetailWeather> {
        return detailRepository.fetchDetailData2()
    }
}