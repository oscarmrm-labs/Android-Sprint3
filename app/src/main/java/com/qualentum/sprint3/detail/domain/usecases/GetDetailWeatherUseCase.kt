package com.qualentum.sprint3.detail.domain.usecases

import com.qualentum.sprint3.detail.data.mappers.DetailWeather
import com.qualentum.sprint3.detail.data.repository.remote.DetailRepository
import javax.inject.Inject


class GetDetailWeatherUseCase @Inject constructor(private val detailRepository: DetailRepository) {
    suspend operator fun invoke(day: String?): Result<DetailWeather> {
        return detailRepository.fetchDetailData2(day)
    }
}