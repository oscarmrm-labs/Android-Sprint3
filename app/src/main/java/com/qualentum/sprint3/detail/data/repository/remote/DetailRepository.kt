package com.qualentum.sprint3.detail.data.repository.remote

import com.qualentum.sprint3.detail.data.model.day.DayDetailResponse

class DetailRepository(
    private val apiService: DetailMeteoAPIService,
    private val day: String,
    private val latitude: Double,
    private val longitude: Double,
) {

    suspend fun fetchDetailData2(): Result<DayDetailResponse> {
        return try {
            val paramsDaily = "temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset,uv_index_max,rain_sum,showers_sum,snowfall_sum"
            val response = apiService.getDayDetail(latitude, longitude, paramsDaily, day, day)
            Result.success(response)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}